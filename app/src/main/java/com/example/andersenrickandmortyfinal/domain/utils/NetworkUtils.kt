package com.example.andersenrickandmortyfinal.domain.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.example.andersenrickandmortyfinal.data.network.connectionmanager.ConnectionManagerImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn

class NetworkUtils(private val context: Context) {
    companion object {
        fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val networkCapabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                return networkCapabilities != null && (
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                        )
            } else {
                val networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnected
            }
        }


        fun isNetworkAvailable2(
            context: Context,
            coroutineScope: CoroutineScope
        ): Flow<Boolean> {

            val connectionManager = ConnectionManagerImpl(context,coroutineScope)
            connectionManager.startListenNetworkState()
         return   connectionManager.isNetworkConnectedFlow

        }


    }
}