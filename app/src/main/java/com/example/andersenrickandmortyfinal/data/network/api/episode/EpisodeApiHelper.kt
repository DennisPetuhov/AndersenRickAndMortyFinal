package com.example.andersenrickandmortyfinal.data.network.api.episode

import com.example.andersenrickandmortyfinal.data.model.episode.EpisodePojo
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import com.example.andersenrickandmortyfinal.data.model.main.TypeOfRequest
import kotlinx.coroutines.flow.Flow

interface EpisodeApiHelper {
    fun getListOfEpisodesByCharacter(
        list: List<Int>
    ): Flow<List<EpisodePojo>>


    fun getAllEpisodesByNameAndEpisode(
        type: TypeOfRequest,
        page: Int,
        query: String,

        ): Flow<PagedResponse<EpisodePojo>>


    fun getSingleEpisodesById(id: Int): Flow<EpisodePojo>

}