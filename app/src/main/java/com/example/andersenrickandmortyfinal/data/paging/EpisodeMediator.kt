package com.example.andersenrickandmortyfinal.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.andersenrickandmortyfinal.data.network.api.episode.EpisodeApiHelper
import com.example.andersenrickandmortyfinal.data.db.characters.Constants
import com.example.andersenrickandmortyfinal.data.db.DatabaseHelper
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.episode.EpisodesRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class EpisodeMediator (

    private val apiHelper: EpisodeApiHelper,
    private val database: DatabaseHelper,
    private val query: String,
    private val type: TypeOfRequest,

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
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey

            }


        }
        try {
            var episodes = PagedResponse<Episode>(null)
            println(" MEDIATOR EPISODE REQUEST  page=$page type=$type, query=$query")
            apiHelper.getAllEpisodesByNameAndEpisode(page=page, type= type, query =  query).collect {
                episodes = it
            }


            val listOfEpisodes = episodes.results
            println(listOfEpisodes)

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
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { episode ->

                database.getNextPageKeySimple(episode.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Episode>): CharacterRemoteKeys? {

        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { episode ->

                database.getNextPageKeySimple(episode.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Episode>
    ): CharacterRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.getNextPageKeySimple(id)
            }
        }
    }

}