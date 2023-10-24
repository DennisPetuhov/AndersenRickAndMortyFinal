package com.example.andersenrickandmortyfinal.data.network.api.episode

import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow

interface EpisodeApiHelper {
    fun getListOfEpisodesByCharacter(
        list: List<Int>
    ): Flow<List<Episode>>


    fun getAllEpisodesByNameAndEpisode(
        type: TypeOfRequest,
        page: Int,
        query: String,

        ): Flow<PagedResponse<Episode>>


  fun  getSingleEpisodesById(id:Int):Flow<Episode>

}