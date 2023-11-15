package com.example.andersenrickandmortyfinal.presenter.ui.locations

import androidx.paging.cachedIn
import com.example.andersenrickandmortyfinal.data.base.BaseMainViewMode
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.data.model.main.MyRequest
import com.example.andersenrickandmortyfinal.data.model.main.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class LocationsViewModel @Inject constructor() : BaseMainViewMode<LocationRick>() {
    init {
        querySearch()

    }

    private val locationQueryFlow =
        MutableStateFlow(MyRequest(TypeOfRequest.None, ""))
    
    @Inject
    @Named("RepositoryOneQualifier")

    lateinit var repo: Repository


    @OptIn(ExperimentalCoroutinesApi::class)
    fun querySearch() {
        viewModelScope.launch {
            locationQueryFlow.flatMapLatest { request ->
                repo.getLocationFromMediator(
                    request.typeOfRequest,
                    request.query,
                ).cachedIn(viewModelScope)

            }.collect {
                _pagingDataFlow.emit(it)
            }


        }
    }

    fun onQueryChanged(query: String) {
        viewModelScope.launch {
            val request = locationQueryFlow.value.copy(query = query)
            locationQueryFlow.emit(request)
        }
    }

    fun onRadioButtonChanged(type: TypeOfRequest) {
        val state = locationQueryFlow.value.copy(typeOfRequest = type)
        locationQueryFlow.value = state

    }


}