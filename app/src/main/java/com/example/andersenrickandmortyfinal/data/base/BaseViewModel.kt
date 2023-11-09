package com.example.andersenrickandmortyfinal.data.base

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.example.andersenrickandmortyfinal.data.navigation.NavigationCommand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {


    private val viewModelJob = Job()
    protected val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    private val _navigation: MutableStateFlow<NavigationCommand> =
        MutableStateFlow(NavigationCommand.Null)
    val navigation: StateFlow<NavigationCommand> get() = _navigation


    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirections(navDirections)
    }

    fun back() {
        _navigation.value = NavigationCommand.Back

    }
}