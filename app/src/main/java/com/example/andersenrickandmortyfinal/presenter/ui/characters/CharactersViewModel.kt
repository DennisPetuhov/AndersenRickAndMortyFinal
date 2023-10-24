package com.example.andersenrickandmortyfinal.presenter.ui.characters

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.base.BaseViewModel
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.character.MyRequest
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor() : BaseViewModel() {
    init {
        querySearch()

    }

    @Inject
    lateinit var repo: Repository


    val queryFlow =
        MutableStateFlow(MyRequest(TypeOfRequest.None, "", "", ""))


    private var _charactersFlow: MutableStateFlow<PagingData<Character>> =
        MutableStateFlow(
            PagingData.empty()
        )
    val charactersFlow: StateFlow<PagingData<Character>>
        get() = _charactersFlow





    fun onQueryChanged(query: String) {
        viewModelScope.launch {
            val typeOfRequest = queryFlow.value.typeOfRequest
            val gender = queryFlow.value.gender
            val status = queryFlow.value.status
            val newRequest = MyRequest(
                typeOfRequest = typeOfRequest,
                query = query,
                gender = gender,
                status = status
            )
            queryFlow.emit(newRequest)
        }
    }

    fun onRadioButtonChanged(type: TypeOfRequest) {
        val state = queryFlow.value.copy(typeOfRequest = type)
        queryFlow.value = state

    }

    fun onSpinnerGenderChanged(gender: String) {
        val state = queryFlow.value.copy(gender = gender)
        queryFlow.value = state
    }

    fun onSpinnerStatusChanged(status: String) {
        val state = queryFlow.value.copy(status = status)
        queryFlow.value = state

    }


    @OptIn(ExperimentalCoroutinesApi::class)
    fun querySearch() {
        viewModelScope.launch {
            queryFlow.flatMapLatest { request ->
                repo.getCharactersFromMediator(
                    request.typeOfRequest,
                    request.query,
                    request.gender,
                    request.status
                ).cachedIn(viewModelScope)

            }.collect {
                _charactersFlow.emit(it)
            }


        }
    }



}