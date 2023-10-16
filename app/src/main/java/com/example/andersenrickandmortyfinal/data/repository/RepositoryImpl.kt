package com.example.andersenrickandmortyfinal.data.repository

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.andersenrickandmortyfinal.data.api.character.CharacterApiHelper
import com.example.andersenrickandmortyfinal.data.api.episode.EpisodeApiHelper
import com.example.andersenrickandmortyfinal.data.api.location.LocationApiHelper
import com.example.andersenrickandmortyfinal.data.db.DatabaseHelper
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import com.example.andersenrickandmortyfinal.data.paging.CharactersMediator
import com.example.andersenrickandmortyfinal.data.paging.EpisodeMediator
import com.example.andersenrickandmortyfinal.data.paging.LocationMediator
import com.example.andersenrickandmortyfinal.data.paging.details.EpisodesInCharacterMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RepositoryImpl @Inject constructor(
    private val characterApiHelper: CharacterApiHelper,
    private val db: DatabaseHelper,
    private val context: Context,
    private val episodeApi: EpisodeApiHelper,
    private val locationApi: LocationApiHelper
) : Repository {


    override fun getCachedEpisodes(
        query: String,
        episodeIds: List<Int>
    ): Flow<PagingData<Episode>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
//                enablePlaceholders = false
            ),
            remoteMediator = EpisodesInCharacterMediator(
                api = episodeApi,
                database = db,
                list = episodeIds
            ),
            pagingSourceFactory = { db.getCachedEpisodes(episodeIds) }
        ).flow
    }

    override suspend fun getPagesOfAllCharacters(
        page: Int, gender: String,
        status: String
    ): Flow<PagedResponse<CharacterRickAndMorty>> {
        return characterApiHelper.getPagesOfAllCharacters(page, gender, status)
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
    ): Flow<PagingData<CharacterRickAndMorty>> {
        Log.d("QUERY SEARCH", "New query: $query + $status + ${type.toString()} + $gender")

        val dbQuery = "%${query.replace(' ', '%')}%"
        val dbGender = "%${query.replace(' ', '%')}%"
        val dbStatus = "%${query.replace(' ', '%')}%"

        println("REPOSITORYIMPL gender=$gender status =$status")


        return Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 3,
                enablePlaceholders = false
            ),
            remoteMediator = CharactersMediator(
                characterApiHelper = characterApiHelper,
                database = db,
                context = context,
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
                db.getAllLocations()

            }

            is TypeOfRequest.Name -> {
                db.findLocationByName(query)
            }

            is TypeOfRequest.Dimension -> {
                db.findLocationByDimension(query)
            }

            is TypeOfRequest.Type -> {
                db.findLocationByType(query)
            }

            else -> {
                db.getAllLocations()
            }
        }
    }

    private fun pagingDataOfEpisode(
        type: TypeOfRequest,
        query: String,
    ): PagingSource<Int, Episode> {
        return when (type) {
            is TypeOfRequest.None -> {
                db.getAllEpisodes()

            }

            is TypeOfRequest.Name -> {
                db.findEpisodeByName(query)
            }

            is TypeOfRequest.Code -> {
                db.findEpisodeByName(query)
            }

            else -> {
                db.getAllEpisodes()
            }
        }
    }

    private fun pagingDataOfCharacter(
        type: TypeOfRequest,
        query: String,
        gender: String,
        status: String
    ): PagingSource<Int, CharacterRickAndMorty> {
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






