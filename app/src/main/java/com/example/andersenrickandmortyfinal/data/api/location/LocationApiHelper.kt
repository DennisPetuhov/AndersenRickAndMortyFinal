package com.example.andersenrickandmortyfinal.data.api.location

import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow

interface LocationApiHelper {
    suspend fun getAllLocations(
        type: TypeOfRequest,
        page: Int,
        query: String,
    ): Flow<PagedResponse<LocationRick>>
}