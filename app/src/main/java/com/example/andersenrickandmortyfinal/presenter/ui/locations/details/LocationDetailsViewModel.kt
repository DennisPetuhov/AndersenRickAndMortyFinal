package com.example.andersenrickandmortyfinal.presenter.ui.locations.details

import android.os.Bundle
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.base.BaseDetailsViewModel
import com.example.andersenrickandmortyfinal.data.base.BaseViewModel
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.location.LocationPojo
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.data.model.location.toEntity
import com.example.andersenrickandmortyfinal.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LocationDetailsViewModel @Inject constructor() : BaseDetailsViewModel<Character>() {


    private var _locationFlow: MutableStateFlow<LocationRick> =
        MutableStateFlow(LocationRick())
    val locationFlow: StateFlow<LocationRick> get() = _locationFlow

    @Inject
    @Named("RepositoryOneQualifier")
    lateinit var repo: Repository

    private fun getArguments(bundle: Bundle): LocationRick {
        val arguments = LocationDetailsFragmentArgs.fromBundle(bundle)
        return arguments.location
    }

    private fun getNotNullArguments(bundle: Bundle): Flow<LocationPojo> {
        val item = getArguments(bundle).id
        return repo.getSingleLocationByIdFromApi(item)


    }


    private fun getNumbersOfCharacters(bundle: Bundle): Flow<MutableList<Int>> {

        getSingleLocationByIdFromInternet(bundle)
        val item = getNotNullArguments(bundle).map {
            val listOfCharacter = it.residents

            val newList = mutableListOf<Int>()
            for (i in listOfCharacter) {
                if (i.isNotBlank()) {
                    val characterNumber = i.filter { symbol ->
                        symbol.isDigit()
                    }.toInt()
                    newList.add(characterNumber)

                }
            }
            return@map newList
        }

        return item

    }


    fun getCharacters(bundle: Bundle) {
        viewModelScope.launch {
            getNumbersOfCharacters(bundle).collect { list ->
                repo.getCachedCharacters(list).cachedIn(viewModelScope)
                    .collect {
                        _pagingDataFlow.emit(it)
                    }
            }

        }
    }


    fun getSingleLocationByIdFromInternet(bundle: Bundle) {
        viewModelScope.launch {
            val location = getArguments(bundle)

            repo.getSingleLocationByIdFromApi(location.id).collect {
                _locationFlow.emit(it.toEntity())
            }


        }

    }


    fun getSingleLocationFromDb(bundle: Bundle) {
        viewModelScope.launch {
            val location = getArguments(bundle)
            repo.findLocationByIdFromDb(location.id).collect {
                _locationFlow.emit(it)
            }


        }

    }

}