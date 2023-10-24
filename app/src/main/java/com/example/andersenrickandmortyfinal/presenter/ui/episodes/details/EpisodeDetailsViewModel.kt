package com.example.andersenrickandmortyfinal.presenter.ui.episodes.details

import android.content.Context
import android.os.Bundle
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.base.BaseViewModel
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.repository.Repository
import com.example.andersenrickandmortyfinal.domain.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel @Inject constructor() : BaseViewModel() {

    @Inject
    lateinit var repo: Repository


    private var _characterFlow: MutableStateFlow<PagingData<Character>> =
        MutableStateFlow(PagingData.empty())
    val characterFlow: StateFlow<PagingData<Character>> get() = _characterFlow


    private var _episodeFlow: MutableStateFlow<Episode> =
        MutableStateFlow(Episode())
    val episodeFlow: StateFlow<Episode> get() = _episodeFlow

fun getSingleEpisodeByIdFromInternet(bundle: Bundle){
    viewModelScope.launch {
        val episode = getArguments(bundle)

        repo.getSingleEpisodesByIdFromApi(episode.id).collect{
            _episodeFlow.emit(it)
        }


    }

}



    fun getSingleEpisodeFromDb(bundle: Bundle) {
        viewModelScope.launch {
            val episode = getArguments(bundle)

          repo.getEpisodeByIdFromDb(episode.id).collect{
              _episodeFlow.emit(it)
          }


        }

    }


    private fun getArguments(bundle: Bundle): Episode {
        val arguments = EpisodeDetailsFragmentArgs.fromBundle(bundle)
        return arguments.episode
    }

    private fun getNumbersOfCharacters(bundle: Bundle): MutableList<Int> {
        val item = getArguments(bundle)
        val listOfCharacter = item.characters
        val newList = mutableListOf<Int>()
        for (i in listOfCharacter) {
            val characterNumber = i.filter { symbol ->
                symbol.isDigit()
            }.toInt()
            newList.add(characterNumber)

        }
        return newList
    }


    fun getCharacters(bundle: Bundle) {
        viewModelScope.launch {
            repo.getCachedCharacters(getNumbersOfCharacters(bundle)).cachedIn(viewModelScope)
                .collect {
                    _characterFlow.emit(it)
                }
        }
    }

}