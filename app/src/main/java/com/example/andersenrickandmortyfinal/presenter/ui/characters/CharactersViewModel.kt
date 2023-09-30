package com.example.andersenrickmorty.presenter.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import com.example.andersenrickandmortyfinal.data.repository.Repository
import com.example.andersenrickandmortyfinal.data.paging.CharacterPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor() : ViewModel() {
    @Inject
    lateinit var repo: Repository


//    @Inject
//    lateinit var characterPagingSource: CharacterPagingSource



    private  var _charactersFlow: MutableStateFlow<PagingData<CharacterRickAndMorty>> = MutableStateFlow(
        PagingData.empty()
    )
    val charactersFlowToMediator: StateFlow<PagingData<CharacterRickAndMorty>>
        get() = _charactersFlow


    val charactersFlow: MutableStateFlow<PagedResponse<CharacterRickAndMorty>> = MutableStateFlow(
        PagedResponse(null)
    )

    fun getCharacters() {
        viewModelScope.launch {

            repo.getAllCharactersForMediator().cachedIn(viewModelScope).collect{
                _charactersFlow.emit(it)
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
//    remoteMediator = mediator
    )
    val pagingData = pager.flow.cachedIn(viewModelScope)
}