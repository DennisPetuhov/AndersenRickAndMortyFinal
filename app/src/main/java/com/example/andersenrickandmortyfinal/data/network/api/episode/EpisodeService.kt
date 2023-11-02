package com.example.andersenrickandmortyfinal.data.network.api.episode

import com.example.andersenrickandmortyfinal.data.model.episode.EpisodePojo
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeService {

    @GET("episode/{list}")
    suspend fun getListOfEpisodesByCharacter(
        @Path("list")
        list: MutableList<Int>
    ): List<EpisodePojo>

    @GET("episode")
    suspend fun getAllEpisodes(
        @Query("page")
        page: Int
    ): PagedResponse<EpisodePojo>

    @GET("episode")
    suspend fun getAllEpisodesByName(
        @Query("page")
        page: Int,
        @Query("name")
        name: String,
    ): PagedResponse<EpisodePojo>

    @GET("episode")
    suspend fun getAllEpisodesByCode(
        @Query("page")
        page: Int,
        @Query("code")
        code: String,
    ): PagedResponse<EpisodePojo>

    @GET("episode/{id}")
    suspend fun getSingleEpisodesById(
        @Path("id")
        page: Int
    ): EpisodePojo
}