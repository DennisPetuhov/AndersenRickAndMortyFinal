package com.example.andersenrickandmortyfinal.data.api.location

import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocationApiHelperImpl @Inject constructor(private val service: LocationService) :
    LocationApiHelper {
    override suspend fun getAllLocations(
        type: TypeOfRequest,
        page: Int,
        query: String,

        ): Flow<PagedResponse<LocationRick>> {
        return flow {
            val response = when (type) {
                is TypeOfRequest.Name -> {
                    service.getAllLocationsByName(page, query)
                }

                is TypeOfRequest.Type -> {
                    service.getAllLocationsByType(page, query)
                }

                is TypeOfRequest.None -> {
                    service.getAllLocations(page)


                }

                is TypeOfRequest.Dimension -> {
                    service.getAllLocationsByDimension(page, query)


                }

                else -> {
                    service.getAllLocations(page)
                }
            }

            emit(response)
        }.flowOn(Dispatchers.IO)


    }
}