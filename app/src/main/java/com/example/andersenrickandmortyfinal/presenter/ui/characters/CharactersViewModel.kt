package com.example.andersenrickmorty.presenter.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.repository.Repository
import com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler.CharacterPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repo: Repository

//    @Inject
//    lateinit var characterPagingSource: CharacterPagingSource


    val charactersFlow: MutableStateFlow<CharacterRickAndMorty> = MutableStateFlow(
        CharacterRickAndMorty(null)
    )

    fun getCharacters() {
        viewModelScope.launch {
            repo.getAllCharacters().flowOn(Dispatchers.IO).collect {
                charactersFlow.emit(it)
            }

        }
    }

    private val pagerConfig = PagingConfig(
        pageSize = 10
    )

    private val pager = Pager(config = pagerConfig, pagingSourceFactory = {
//        characterPagingSource
        CharacterPagingSource(repo)
    })
    val pagingData = pager.flow.cachedIn(viewModelScope)
}