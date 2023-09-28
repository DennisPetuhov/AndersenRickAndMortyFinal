package com.example.andersenrickmorty.presenter.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.model.character.ResultRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import com.example.andersenrickandmortyfinal.data.repository.Repository
import com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler.CharacterPagingSource
import com.example.andersenrickandmortyfinal.presenter.ui.characters.recycler.CharactersMediator
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


    val charactersFlow: MutableStateFlow<PagedResponse<ResultRickAndMorty>> = MutableStateFlow(
        PagedResponse(null)
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

    @OptIn(ExperimentalPagingApi::class)
    private val pager = Pager(config = pagerConfig, pagingSourceFactory = {
//        characterPagingSource
        CharacterPagingSource(repo)
    },
//    remoteMediator = CharactersMediator()
    )
    val pagingData = pager.flow.cachedIn(viewModelScope)
}