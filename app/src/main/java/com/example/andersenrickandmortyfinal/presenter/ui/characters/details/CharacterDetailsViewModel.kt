package com.example.andersenrickandmortyfinal.presenter.ui.characters.details

import android.os.Bundle
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.api.episode.EpisodeApiHelper
import com.example.andersenrickandmortyfinal.data.base.BaseViewModel
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
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



    private var _episodeFlow: MutableStateFlow<List<Episode>> =
        MutableStateFlow(mutableListOf(Episode()))
    val episodeFlow: StateFlow<List<Episode>>
        get() = _episodeFlow
    private var _listStringFlow: MutableStateFlow<MutableList<String>> =
        MutableStateFlow(mutableListOf())


    private fun getNumbersOfEpisodes(bundle: Bundle): MutableList<Int> {
        val item = arguments(bundle)

        val episodes = item.episode
//        val episodes = _singleCharacterFlow.value.episode

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

    fun arguments(bundle: Bundle): CharacterRickAndMorty {
        val arguments = CharacterDetailsFragmentArgs.fromBundle(bundle)
        return arguments.character
    }

    var _episodeFlowTry: MutableStateFlow<PagingData<Episode>> =
        MutableStateFlow(PagingData.empty())

    fun foo(bundel:Bundle) {
        viewModelScope.launch {
            repo.getCachedEpisodes("", getNumbersOfEpisodes(bundel)).cachedIn(viewModelScope)
                .collect {
                    _episodeFlowTry.emit(it)
                }
        }
    }


}