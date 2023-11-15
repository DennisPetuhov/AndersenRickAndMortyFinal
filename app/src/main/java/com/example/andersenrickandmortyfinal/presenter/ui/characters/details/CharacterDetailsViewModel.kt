package com.example.andersenrickandmortyfinal.presenter.ui.characters.details

import android.os.Bundle
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.base.BaseDetailsViewModel
import com.example.andersenrickandmortyfinal.data.base.BaseViewModel
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.character.toEntity
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor() : BaseDetailsViewModel<Episode>() {


    @Inject
    @Named("RepositoryOneQualifier")
    lateinit var repo: Repository

    private var _characterFlow: MutableStateFlow<Character> =
        MutableStateFlow(Character())
    val characterFlow: StateFlow<Character> get() = _characterFlow


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

            newList.add(episodeNumber)

        }
        return newList
    }

    fun findCharacterInDb(bundle: Bundle) {
        val id = getArguments(bundle).id
        viewModelScope.launch {
            repo.findCharacterByIdInDb(id).collect {
                _characterFlow.emit(it)
            }
        }

    }

    fun getCharacterFromApi(bundle: Bundle) {
        val id = getArguments(bundle).id
        viewModelScope.launch {
            repo.getCharacterByIdFromApi(id).collect {
                _characterFlow.emit(it.toEntity())
            }
        }

    }

    fun getEpisodes(bundle: Bundle) {
        viewModelScope.launch {
            repo.getCachedEpisodes(getNumbersOfEpisodes(bundle)).cachedIn(viewModelScope)
                .collect {
                    _pagingDataFlow.emit(it)
                }
        }
    }

    fun getLocation(bundle: Bundle): LocationRick {
        val item = getArguments(bundle)
        val id = item.location.url.filter {
            it.isDigit()

        }.toInt()

        return LocationRick(
            id = id, url = item.location.url, name = item.location.name
        )

    }

    fun getOrigin(bundle: Bundle): LocationRick {
        val item = getArguments(bundle)
        var location = LocationRick()
        if (item.origin.url.isNotBlank()) {

            val id = item.origin.url.filter {
                it.isDigit()
            }.toInt()
            location = LocationRick(
                id = id, url = item.location.url, name = item.location.name
            )
        }

        return location

    }


}