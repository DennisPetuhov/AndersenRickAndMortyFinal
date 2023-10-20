package com.example.andersenrickandmortyfinal.presenter.ui.characters.details

import android.os.Bundle
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.network.api.episode.EpisodeApiHelper
import com.example.andersenrickandmortyfinal.data.base.BaseViewModel
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var apiEpisodeApiHelper: EpisodeApiHelper

    @Inject
    lateinit var repo: Repository

    private var _listStringFlow: MutableStateFlow<MutableList<String>> =
        MutableStateFlow(mutableListOf())


    private var _episodesFlow: MutableStateFlow<PagingData<Episode>> =
        MutableStateFlow(PagingData.empty())
    val episodesFlow: StateFlow<PagingData<Episode>> get() = _episodesFlow


    fun getArguments(bundle: Bundle): Character {
        val arguments = CharacterDetailsFragmentArgs.fromBundle(bundle)
        return arguments.character
    }

    private fun getNumbersOfEpisodes(bundle: Bundle): MutableList<Int> {
        val item = getArguments(bundle)
        val episodes = item.episode
        val newList = mutableListOf<Int>()
        for (i in episodes) {
            val episodeNumber = i.filter { symbol ->
                symbol.isDigit()
            }.toInt()
            println(episodeNumber)
            newList.add(episodeNumber)

        }
        return newList
    }


    fun getEpisodes(bundle: Bundle) {
        viewModelScope.launch {
            repo.getCachedEpisodes(getNumbersOfEpisodes(bundle)).cachedIn(viewModelScope)
                .collect {
                    _episodesFlow.emit(it)
                }
        }
    }


}