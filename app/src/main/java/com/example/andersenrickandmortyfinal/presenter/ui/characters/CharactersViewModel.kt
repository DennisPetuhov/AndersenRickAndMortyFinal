package com.example.andersenrickmorty.presenter.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
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


    private var _charactersFlow: MutableStateFlow<PagingData<CharacterRickAndMorty>> =
        MutableStateFlow(
            PagingData.empty()
        )
    val charactersFlowToMediator: StateFlow<PagingData<CharacterRickAndMorty>>
        get() = _charactersFlow


    fun getCharacters(name: String) {
        viewModelScope.launch {

            repo.getAllCharactersForMediator(name).cachedIn(viewModelScope).collect {
                _charactersFlow.emit(it)
            }

        }
    }


}