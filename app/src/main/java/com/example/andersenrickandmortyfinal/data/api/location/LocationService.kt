package com.example.andersenrickandmortyfinal.data.api.location

import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationService {

    @GET("location")
    suspend fun getAllLocations(
        @Query("page")
        page: Int
    ): PagedResponse<LocationRick>

    @GET("location")
    suspend fun getAllLocationsByDimension(
        @Query("page")
        page: Int,
        @Query("dimension")
        dimension: String
    ): PagedResponse<LocationRick>

    @GET("location")
    suspend fun getAllLocationsByType(
        @Query("page")
        page: Int,
        @Query("type")
        type: String
    ): PagedResponse<LocationRick>

    @GET("location")
    suspend fun getAllLocationsByName(
        @Query("page")
        page: Int,
        @Query("name")
        name: String
    ): PagedResponse<LocationRick>
}