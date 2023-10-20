package com.example.andersenrickandmortyfinal.data.network.connectionmanager

import kotlinx.coroutines.flow.StateFlow

interface ConnectionManager {
    val isNetworkConnectedFlow: StateFlow<Boolean>

    val isNetworkConnected: Boolean

    fun startListenNetworkState()

    fun stopListenNetworkState()
}