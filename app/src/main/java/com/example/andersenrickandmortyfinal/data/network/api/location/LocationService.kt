package com.example.andersenrickandmortyfinal.data.network.api.location

import com.example.andersenrickandmortyfinal.data.model.location.LocationPojo
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationService {

    @GET("location")
    suspend fun getAllLocations(
        @Query("page")
        page: Int
    ): PagedResponse<LocationPojo>

    @GET("location")
    suspend fun getAllLocationsByDimension(
        @Query("page")
        page: Int,
        @Query("dimension")
        dimension: String
    ): PagedResponse<LocationPojo>

    @GET("location")
    suspend fun getAllLocationsByType(
        @Query("page")
        page: Int,
        @Query("type")
        type: String
    ): PagedResponse<LocationPojo>

    @GET("location")
    suspend fun getAllLocationsByName(
        @Query("page")
        page: Int,
        @Query("name")
        name: String
    ): PagedResponse<LocationPojo>

    @GET("location/{id}")
    suspend fun getLocationById(@Path("id") id: Int): LocationPojo
}