package com.example.andersenrickandmortyfinal.data.repository

import androidx.paging.PagingData
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getPagesOfAllCharacters(
        page: Int, gender: String,
        status: String
    ): Flow<PagedResponse<Character>>


    fun getNextPageKey(id: Int): Flow<CharacterRemoteKeys?>

    fun getCharactersFromMediator(
        type: TypeOfRequest,
        query: String,
        gender: String,
        status: String
    ): Flow<PagingData<Character>>

    fun getEpisodesFromMediator(
        type: TypeOfRequest,
        query: String,

    ): Flow<PagingData<Episode>>

    fun getLocationFromMediator(
        type: TypeOfRequest,
        query: String,

        ): Flow<PagingData<LocationRick>>


    fun getCachedEpisodes(episodeIds:List<Int>): Flow<PagingData<Episode>>
    fun getCachedCharacters(characterIds:List<Int>): Flow<PagingData<Character>>
    fun getEpisodeByIdFromDb(id: Int):Flow<Episode>
    fun getSingleEpisodesByIdFromApi(id: Int): Flow<Episode>
}