package com.example.andersenrickandmortyfinal.data.db

import androidx.paging.PagingSource
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.episode.EpisodesRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.location.LocationRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import kotlinx.coroutines.flow.Flow

interface DatabaseHelper {
    suspend fun insertAllCharacters(list: List<CharacterRickAndMorty>)
    suspend fun insertAllCharactersKeys(list: List<CharacterRemoteKeys>)


    suspend fun deleteAllCharacters()
    suspend fun deleteAllCharactersKeys()

    fun getNextPageKey(id: Int): Flow<CharacterRemoteKeys?>

    suspend fun getNextPageKeySimple(id: Int): CharacterRemoteKeys?

    fun pagingSource(): PagingSource<Int, CharacterRickAndMorty>

    fun findCharacterByName(
        queryString: String,
        gender: String,
        status: String
    ): PagingSource<Int, CharacterRickAndMorty>

    fun findCharacterBySpecies(
        queryString: String,
        gender: String,
        status: String
    ): PagingSource<Int, CharacterRickAndMorty>

    fun findCharacterByType(
        queryString: String,
        gender: String,
        status: String
    ): PagingSource<Int, CharacterRickAndMorty>

    fun findAllCharacters(
        queryString: String,
        gender: String,
        status: String
    ): PagingSource<Int, CharacterRickAndMorty>

    suspend fun insertAllEpisodes(list: List<Episode>)
    suspend fun insertAllEpisodesKeys(list: List<EpisodesRemoteKeys>)


    suspend fun deleteAllEpisodes()
    suspend fun deleteAllEpisodesKeys()

    fun getAllEpisodes(): PagingSource<Int, Episode>


    fun findEpisodeByName(queryString: String): PagingSource<Int, Episode>


    fun findEpisodeByCode(queryString: String): PagingSource<Int, Episode>

     fun getCachedEpisodes(episodeIds:List<Int>): PagingSource<Int,Episode>



    suspend fun insertAllLocations(list: List<LocationRick>)
    suspend fun insertAllLocationsKeys(list: List<LocationRemoteKeys>)


    suspend fun deleteAllLocations()
    suspend fun deleteAllLocationsKeys()


    fun getAllLocations(): PagingSource<Int, LocationRick>


    fun findLocationByName(queryString: String): PagingSource<Int, LocationRick>


    fun findLocationByDimension(queryString: String): PagingSource<Int, LocationRick>
    fun findLocationByType(queryString: String): PagingSource<Int, LocationRick>
}