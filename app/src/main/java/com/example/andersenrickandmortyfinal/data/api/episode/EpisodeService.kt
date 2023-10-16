package com.example.andersenrickandmortyfinal.data.api.episode

import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeService {

    @GET("episode/{list}")
    suspend fun getListOfEpisodesByCharacter(
        @Path("list")
        list: MutableList<Int>
    ): List<Episode>

    @GET("episode")
    suspend fun getAllEpisodes(
        @Query("page")
        page: Int
    ): PagedResponse<Episode>

    @GET("episode")
    suspend fun getAllEpisodesByName(
        @Query("page")
        page: Int,
        @Query("name")
        name: String,
    ): PagedResponse<Episode>

    @GET("episode")
    suspend fun getAllEpisodesByCode(
        @Query("page")
        page: Int,
        @Query("code")
        code: String,
    ): PagedResponse<Episode>
}