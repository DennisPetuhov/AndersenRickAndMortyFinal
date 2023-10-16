package com.example.andersenrickmorty.presenter.ui.locations

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.base.BaseViewModel
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.character.MyRequest
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor() : BaseViewModel() {
    init {
        querySearch()

    }
    val queryFlow =
        MutableStateFlow(MyRequest(TypeOfRequest.None, "", "", ""))

    private var _locationFlow: MutableStateFlow<PagingData<LocationRick>> =
        MutableStateFlow(
            PagingData.empty()
        )
    val locationFlow: StateFlow<PagingData<LocationRick>>
        get() = _locationFlow


    @Inject
    lateinit var repo: Repository


    @OptIn(ExperimentalCoroutinesApi::class)
    fun querySearch() {
        viewModelScope.launch {
            queryFlow.flatMapLatest { request ->
                repo.getLocationFromMediator(
                    request.typeOfRequest,
                    request.query,
                ).cachedIn(viewModelScope)

            }.collect {
                _locationFlow.emit(it)
            }


        }
    }


}