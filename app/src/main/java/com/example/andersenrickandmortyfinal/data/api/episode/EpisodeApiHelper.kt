package com.example.andersenrickandmortyfinal.data.api.episode

import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow

interface EpisodeApiHelper {
    fun getListOfEpisodesByCharacter(
        list: List<Int>
    ): Flow<List<Episode>>


    fun getAllEpisodesByNameAndCode(
        type: TypeOfRequest,
        page: Int,
        query: String,

        ): Flow<PagedResponse<Episode>>

}