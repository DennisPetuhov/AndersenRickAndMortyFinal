package com.example.andersenrickandmortyfinal.presenter.ui.episodes

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.base.BaseViewModel
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.main.MyRequest
import com.example.andersenrickandmortyfinal.data.model.main.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor() : BaseViewModel() {
    init {
        initSearch()

    }

    @Inject
    lateinit var repo: Repository


    private val episodeQueryFlow =
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
            episodeQueryFlow.flatMapLatest { request ->
                repo.getEpisodesFromMediator(
                    request.typeOfRequest,
                    request.query,
                ).cachedIn(viewModelScope)

            }.collect {
                _episodeFlow.emit(it)
            }


        }
    }

    fun onQueryChanged(query: String) {
        viewModelScope.launch {
            val request = episodeQueryFlow.value.copy(query = query)
            episodeQueryFlow.emit(request)
        }
    }

    fun onRadioButtonChanged(type: TypeOfRequest) {
        val state = episodeQueryFlow.value.copy(typeOfRequest = type)
        episodeQueryFlow.value = state

    }


}