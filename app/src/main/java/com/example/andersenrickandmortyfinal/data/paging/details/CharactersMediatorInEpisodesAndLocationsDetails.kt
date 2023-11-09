package com.example.andersenrickandmortyfinal.data.paging.details

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.andersenrickandmortyfinal.data.db.DatabaseHelper
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.character.CharacterPojo
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.toEntity
import com.example.andersenrickandmortyfinal.data.network.api.character.CharacterApiHelper
import com.example.andersenrickandmortyfinal.domain.utils.Constants
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharactersMediatorInEpisodesAndLocationsDetails(
    private val api: CharacterApiHelper,
    private val db: DatabaseHelper,
    private val list: List<Int>,
) : RemoteMediator<Int, Character>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
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
            var myList = listOf<CharacterPojo>()
            api.getListOfCharacters(list).collect {
                myList = it
            }


            val listOfPojo = myList

            val endOfPaginationReached = listOfPojo.isEmpty()
            if (loadType == LoadType.REFRESH) {
                db.deleteAllCharacters()
                db.deleteAllCharactersKeys()
            }

            val prevKey = if (page == Constants.STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1


            val keys = listOfPojo.map {
                CharacterRemoteKeys(characterId = it.id, prevKey = prevKey, nextKey = nextKey)
            }

            db.insertAllCharactersKeys(keys)


            val listOfCharacter = listOfPojo.map {
                it.toEntity()
            }
            db.insertAllCharacters(listOfCharacter)




            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Character>): CharacterRemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { character ->

                db.getNextPageKeySimple(character.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Character>): CharacterRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { character ->

                db.getNextPageKeySimple(character.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Character>
    ): CharacterRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { episode ->
                db.getNextPageKeySimple(episode)
            }
        }
    }

}