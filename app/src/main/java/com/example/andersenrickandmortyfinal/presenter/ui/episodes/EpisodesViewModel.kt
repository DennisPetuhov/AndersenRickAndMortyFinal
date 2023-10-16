package com.example.andersenrickandmortyfinal.presenter.ui.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.model.character.MyRequest
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor() : ViewModel() {
    init {
        initSearch()

    }

    @Inject
    lateinit var repo: Repository


    val queryFlow =
        MutableStateFlow(MyRequest(TypeOfRequest.None, "", "", ""))

    private var _episodeFlow: MutableStateFlow<PagingData<Episode>> =
        MutableStateFlow(
            PagingData.empty()
        )
    val episodesFlow: StateFlow<PagingData<Episode>>
        get() = _episodeFlow


    @OptIn(ExperimentalCoroutinesApi::class)
    fun initSearch() {
        viewModelScope.launch {
            queryFlow.flatMapLatest { request ->
                repo.getEpisodesFromMediator(
                    request.typeOfRequest,
                    request.query,
                ).cachedIn(viewModelScope)

            }.collect {
                _episodeFlow.emit(it)
            }


        }
    }



}