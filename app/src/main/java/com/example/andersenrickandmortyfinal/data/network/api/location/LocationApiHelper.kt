package com.example.andersenrickandmortyfinal.data.network.api.location

import com.example.andersenrickandmortyfinal.data.model.location.LocationPojo
import com.example.andersenrickandmortyfinal.data.model.main.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow

interface LocationApiHelper {
    suspend fun getAllLocations(
        type: TypeOfRequest,
        page: Int,
        query: String,
    ): Flow<PagedResponse<LocationPojo>>

   fun getLocationById(id: Int): Flow<LocationPojo>
}