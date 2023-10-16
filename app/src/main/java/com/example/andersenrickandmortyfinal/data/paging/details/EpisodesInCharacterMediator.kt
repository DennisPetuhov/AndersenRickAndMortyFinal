package com.example.andersenrickandmortyfinal.data.paging.details

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.andersenrickandmortyfinal.data.api.episode.EpisodeApiHelper
import com.example.andersenrickandmortyfinal.data.db.DatabaseHelper
import com.example.andersenrickandmortyfinal.data.db.characters.Constants
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.episode.EpisodesRemoteKeys
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class EpisodesInCharacterMediator(
    private val api: EpisodeApiHelper,
    private val database: DatabaseHelper,
    private val list: List<Int>,

//    private val query: String,
//private val type: TypeOfRequest,
//
//private val gender: String,
//private val status: String

) : RemoteMediator<Int, Episode>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Episode>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: Constants.STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey

            }


        }
        try {
            var myList = listOf<Episode>()
//            println(" MEDIATOR REQUEST  page=$page type=$type, query=$query, gender=$gender, status=$status")
            api.getListOfEpisodesByCharacter(list).collect {
                myList = it
            }


            val listOfEpisodes = myList

            val endOfPaginationReached = listOfEpisodes.isEmpty()
            if (loadType == LoadType.REFRESH) {
                database.deleteAllEpisodes()
                database.deleteAllEpisodesKeys()
            }

            val prevKey = if (page == Constants.STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1

            println("!!!!  prevKey $prevKey nextKey $nextKey")


            val keys = listOfEpisodes.map {
                EpisodesRemoteKeys(characterId = it.id, prevKey = prevKey, nextKey = nextKey)
            }

            database.insertAllEpisodesKeys(keys)

            database.insertAllEpisodes(listOfEpisodes)




            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Episode>): CharacterRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { rick ->

                database.getNextPageKeySimple(rick.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Episode>): CharacterRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { episode ->
                // Get the remote keys of the first items retrieved
                database.getNextPageKeySimple(episode.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Episode>
    ): CharacterRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { episode ->
                database.getNextPageKeySimple(episode)
            }
        }
    }

}