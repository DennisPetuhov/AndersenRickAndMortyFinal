package com.example.andersenrickandmortyfinal.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.andersenrickandmortyfinal.data.api.location.LocationApiHelper
import com.example.andersenrickandmortyfinal.data.db.DatabaseHelper
import com.example.andersenrickandmortyfinal.data.db.characters.Constants
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.location.LocationRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class LocationMediator
    (
    private val apiHelper: LocationApiHelper,
    private val database: DatabaseHelper,
    private val query: String,
    private val type: TypeOfRequest,

    ) : RemoteMediator<Int, LocationRick>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocationRick>
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
            var locations = PagedResponse<LocationRick>(null)
            println(" MEDIATOR Location REQUEST  page=$page type=$type, query=$query")
            apiHelper.getAllLocations(page = page, type = type, query = query).collect {
                locations = it
            }


            val listOfLocations = locations.results
            println(listOfLocations)

            val endOfPaginationReached = listOfLocations.isEmpty()
            if (loadType == LoadType.REFRESH) {
                database.deleteAllLocations()
                database.deleteAllLocationsKeys()
            }

            val prevKey = if (page == Constants.STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1

            println("!!!!  prevKey $prevKey nextKey $nextKey")


            val keys = listOfLocations.map {
                LocationRemoteKeys(locationId = it.id, prevKey = prevKey, nextKey = nextKey)
            }

            database.insertAllLocationsKeys(keys)

            database.insertAllLocations(listOfLocations)




            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, LocationRick>): CharacterRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { location ->

                database.getNextPageKeySimple(location.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, LocationRick>): CharacterRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { location ->
                // Get the remote keys of the first items retrieved
                database.getNextPageKeySimple(location.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, LocationRick>
    ): CharacterRemoteKeys? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.getNextPageKeySimple(id)
            }
        }
    }
}
