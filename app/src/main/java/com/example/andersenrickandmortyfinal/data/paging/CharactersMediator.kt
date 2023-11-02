package com.example.andersenrickandmortyfinal.data.paging


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.andersenrickandmortyfinal.data.db.DatabaseHelper
import com.example.andersenrickandmortyfinal.data.db.characters.Constants.STARTING_PAGE_INDEX
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.character.CharacterPojo
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.toEntity
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import com.example.andersenrickandmortyfinal.data.model.main.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.network.api.character.CharacterApiHelper
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharactersMediator(
    private val characterApiHelper: CharacterApiHelper,
    private val database: DatabaseHelper,
    private val query: String,
    private val type: TypeOfRequest,

    private val gender: String,
    private val status: String

) : RemoteMediator<Int, Character>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Character>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
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
            var characters = PagedResponse<CharacterPojo>(null)

            characterApiHelper.getCharactersByQueryFlow(page, type, query, gender, status).collect {
                characters = it
            }


            val listOfPojo = characters.results

            val endOfPaginationReached = listOfPojo.isEmpty()
            if (loadType == LoadType.REFRESH) {
                database.deleteAllCharacters()
                database.deleteAllCharactersKeys()
            }

            val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1


            val keys = listOfPojo.map {
                CharacterRemoteKeys(characterId = it.id, prevKey = prevKey, nextKey = nextKey)
            }
            val listOfCharacter = listOfPojo.map {
                it.toEntity()
            }


            database.insertAllCharactersKeys(keys)

            database.insertAllCharacters(listOfCharacter)




            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Character>): CharacterRemoteKeys? {

        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { rick ->

                database.getNextPageKeySimple(rick.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Character>): CharacterRemoteKeys? {

        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { rick ->

                database.getNextPageKeySimple(rick.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Character>
    ): CharacterRemoteKeys? {

        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { rickId ->
                database.getNextPageKeySimple(rickId)
            }
        }
    }

}