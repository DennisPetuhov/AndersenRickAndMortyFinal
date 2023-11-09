package com.example.andersenrickandmortyfinal.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.andersenrickandmortyfinal.data.db.DatabaseHelper
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.character.CharacterPojo
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.episode.EpisodePojo
import com.example.andersenrickandmortyfinal.data.model.location.LocationPojo
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import com.example.andersenrickandmortyfinal.data.model.main.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.network.api.character.CharacterApiHelper
import com.example.andersenrickandmortyfinal.data.network.api.episode.EpisodeApiHelper
import com.example.andersenrickandmortyfinal.data.network.api.location.LocationApiHelper
import com.example.andersenrickandmortyfinal.data.paging.CharactersMediator
import com.example.andersenrickandmortyfinal.data.paging.EpisodeMediator
import com.example.andersenrickandmortyfinal.data.paging.LocationMediator
import com.example.andersenrickandmortyfinal.data.paging.details.CharactersMediatorInEpisodesAndLocationsDetails
import com.example.andersenrickandmortyfinal.data.paging.details.EpisodesInCharacterMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RepositoryImpl @Inject constructor(
    private val characterApi: CharacterApiHelper,
    private val db: DatabaseHelper,
    private val episodeApi: EpisodeApiHelper,
    private val locationApi: LocationApiHelper
) : Repository {


    override fun getCachedEpisodes(
        episodeIds: List<Int>
    ): Flow<PagingData<Episode>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = EpisodesInCharacterMediator(
                api = episodeApi,
                database = db,
                listOfEpisodes = episodeIds
            ),
            pagingSourceFactory = { db.getCachedEpisodes(episodeIds) }
        ).flow
    }

    override fun getCachedCharacters(characterIds: List<Int>): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = CharactersMediatorInEpisodesAndLocationsDetails(
                api = characterApi,
                db = db,
                list = characterIds
            ),
            pagingSourceFactory = { db.findCachedCharters(characterIds) }


        ).flow
    }

    override fun getEpisodeByIdFromDb(id: Int): Flow<Episode> {
        return db.getEpisodeById(id)
    }

    override fun getSingleEpisodesByIdFromApi(id: Int): Flow<EpisodePojo> {
        return episodeApi.getSingleEpisodesById(id)
    }

    override fun findCharacterByIdInDb(id: Int): Flow<Character> {
        return db.findCharacterById(id)
    }

    override suspend fun getCharacterByIdFromApi(id: Int): Flow<CharacterPojo> {
        return characterApi.getCharacterById(id)
    }

    override fun getSingleLocationByIdFromApi(id: Int): Flow<LocationPojo> {
        return locationApi.getLocationById(id)
    }

    override fun findLocationByIdFromDb(id: Int): Flow<LocationRick> {
        return db.findLocationById(id)
    }

    override suspend fun getPagesOfAllCharacters(
        page: Int, gender: String,
        status: String
    ): Flow<PagedResponse<CharacterPojo>> {
        return characterApi.getPagesOfAllCharacters(page, gender, status)
    }


    override fun getNextPageKey(id: Int): Flow<CharacterRemoteKeys?> {
        return db.getNextPageKey(id)
    }


    @OptIn(ExperimentalPagingApi::class)
    override fun getCharactersFromMediator(
        type: TypeOfRequest,
        query: String,
        gender: String,
        status: String
    ): Flow<PagingData<Character>> {

        val dbQuery = "%${query.replace(' ', '%')}%"
        val dbGender = "%${query.replace(' ', '%')}%"
        val dbStatus = "%${query.replace(' ', '%')}%"


        return Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 3,
                enablePlaceholders = false
            ),
            remoteMediator = CharactersMediator(
                characterApiHelper = characterApi,
                database = db,
                query = query,
                type = type,
                gender = gender,
                status = status

            )
        )
        {

            pagingDataOfCharacter(type, dbQuery, dbGender, dbStatus)

        }.flow


    }

    override fun getEpisodesFromMediator(
        type: TypeOfRequest,
        query: String
    ): Flow<PagingData<Episode>> {
        Log.d("QUERY SEARCH", "New query: $query")
        val dbQuery = "%${query.replace(' ', '%')}%"

        return Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 3,
                enablePlaceholders = false
            ),
            remoteMediator = EpisodeMediator(
                apiHelper = episodeApi,
                database = db,

                query = query,
                type = type

            )
        )
        {

            pagingDataOfEpisode(type, dbQuery)

        }.flow

    }

    override fun getLocationFromMediator(
        type: TypeOfRequest,
        query: String
    ): Flow<PagingData<LocationRick>> {
        Log.d("QUERY SEARCH", "New query: $query")
        val dbQuery = "%${query.replace(' ', '%')}%"

        return Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 3,
                enablePlaceholders = false
            ),
            remoteMediator = LocationMediator(
                apiHelper = locationApi,
                database = db,

                query = query,
                type = type

            )
        ) {

            pagingDataOfLocation(type, dbQuery)

        }.flow
    }

    private fun pagingDataOfLocation(
        type: TypeOfRequest,
        query: String,
    ): PagingSource<Int, LocationRick> {
        return when (type) {
            is TypeOfRequest.None -> {
                db.getAllLocations(query)

            }

            is TypeOfRequest.Name -> {
                db.findLocationByName(query)
            }

            is TypeOfRequest.Dimension -> {
                db.findLocationByDimension(query)
            }

            is TypeOfRequest.Type -> {
                db.findLocationByCode(query)
            }

            else -> {
                db.getAllLocations(query)
            }
        }
    }

    private fun pagingDataOfEpisode(
        type: TypeOfRequest,
        query: String,
    ): PagingSource<Int, Episode> {
        return when (type) {
            is TypeOfRequest.None -> {

                db.getAllEpisodes(query)

            }

            is TypeOfRequest.Name -> {
                db.findEpisodeByName(query)
            }

            is TypeOfRequest.Episode -> {
                db.findEpisodeByEpisode(query)
            }

            else -> {
                db.getAllEpisodes(query)
            }
        }
    }

    private fun pagingDataOfCharacter(
        type: TypeOfRequest,
        query: String,
        gender: String,
        status: String
    ): PagingSource<Int, Character> {
        return when (type) {
            is TypeOfRequest.None -> {
                db.findAllCharacters(query, gender, status)

            }

            is TypeOfRequest.Name -> {
                db.findCharacterByName(query, gender, status)
            }

            is TypeOfRequest.Species -> {
                db.findCharacterBySpecies(query, gender, status)
            }

            is TypeOfRequest.Type -> {
                db.findCharacterByType(query, gender, status)
            }

            else -> {
                db.findAllCharacters(query, gender, status)
            }
        }
    }


}






