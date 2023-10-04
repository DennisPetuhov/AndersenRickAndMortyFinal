package com.example.andersenrickandmortyfinal.presenter.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.character.CharacterTypeRequest
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor() : ViewModel() {
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

    fun onSpinerGenderChanged(gender: String) {
        val state = queryFlow.value.copy(gender = gender)
        queryFlow.value = state
    }

    fun onSpinnerStatusChanged(status: String) {
        val state = queryFlow.value.copy(status = status)
        queryFlow.value = state

    }

    fun getCharacters(type: TypeOfRequest, query: String, gender: String, status: String) {
        viewModelScope.launch {

            repo.getAllCharactersFromMediator(type, query, gender, status).cachedIn(viewModelScope)
                .collect {
                    _charactersFlow.emit(it)
                }


        }
    }


}