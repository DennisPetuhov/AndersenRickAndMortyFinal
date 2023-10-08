package com.example.andersenrickandmortyfinal.data.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

abstract class BaseViewModel : ViewModel() {

    // Создаем корутины для обработки асинхронных операций
    private val viewModelJob = Job()
    protected val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        // Очищаем корутины, когда ViewModel уничтожается
        viewModelJob.cancel()
    }
}