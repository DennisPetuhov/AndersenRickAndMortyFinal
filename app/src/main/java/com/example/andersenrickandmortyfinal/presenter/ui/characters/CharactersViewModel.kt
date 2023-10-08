package com.example.andersenrickandmortyfinal.presenter.ui.characters

import androidx.paging.PagingData
import com.example.andersenrickandmortyfinal.data.base.BaseViewModel
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.character.CharacterTypeRequest
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
        MutableStateFlow(CharacterTypeRequest(TypeOfRequest.None, "", "", ""))


    private var _charactersFlow: MutableStateFlow<PagingData<CharacterRickAndMorty>> =
        MutableStateFlow(
            PagingData.empty()
        )
    val charactersFlowToMediator: StateFlow<PagingData<CharacterRickAndMorty>>
        get() = _charactersFlow

    fun onQueryChanged(query: String) {
        viewModelScope.launch {
            val typeOfRequest = queryFlow.value.typeOfRequest
            val gender = queryFlow.value.gender
            val status = queryFlow.value.status
            val newRequest = CharacterTypeRequest(
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
                repo.getAllCharactersFromMediator(
                    request.typeOfRequest,
                    request.query,
                    request.gender,
                    request.status
                )

            }.collect {
                _charactersFlow.emit(it)
            }


        }
    }


//    fun getCharacters(type: TypeOfRequest, query: String, gender: String, status: String) {
//        viewModelScope.launch {
//            val newFlow =
//                repo.getAllCharactersFromMediator(type, query, gender, status)
//                    .cachedIn(viewModelScope)
//            newFlow.collect {
//                _charactersFlow.emit(it)
//            }
//
//
//        }
//    }


}