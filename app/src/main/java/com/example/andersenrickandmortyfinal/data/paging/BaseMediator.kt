//package com.example.andersenrickandmortyfinal.data.paging
//import android.content.Context
//import androidx.paging.ExperimentalPagingApi
//import androidx.paging.LoadType
//import androidx.paging.PagingState
//import androidx.paging.RemoteMediator
//import com.example.andersenrickandmortyfinal.data.api.ApiHelper
//import com.example.andersenrickandmortyfinal.data.db.characters.Constants.STARTING_PAGE_INDEX
//import com.example.andersenrickandmortyfinal.data.db.characters.DatabaseHelper
//import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
//import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
//import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
//import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
//import retrofit2.HttpException
//import java.io.IOException
//import javax.inject.Inject
//
//@OptIn(ExperimentalPagingApi::class)
//abstract class BaseMediator
// @Inject constructor(
//    private val apiHelper: ApiHelper,
//    private val database: DatabaseHelper,
//    private val context: Context,
//    private val query: String,
//    private  val type:TypeOfRequest,
//    private val gender:String,
//    private val status:String
//
//) : RemoteMediator<Int, CharacterRickAndMorty>() {
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, CharacterRickAndMorty>
//    ): MediatorResult {
//        val page = when (loadType) {
//            LoadType.REFRESH -> {
//                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                remoteKeys?.nextKey?.minus(1) ?: STARTING_PAGE_INDEX
//            }
//
//            LoadType.PREPEND -> {
//                val remoteKeys = getRemoteKeyForFirstItem(state)
//                val prevKey = remoteKeys?.prevKey
//                if (prevKey == null) {
//                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                }
//                prevKey
//            }
//
//            LoadType.APPEND -> {
//                val remoteKeys = getRemoteKeyForLastItem(state)
//                val nextKey = remoteKeys?.nextKey
//                if (nextKey == null) {
//                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                }
//                nextKey
//
//            }
//
//
//        }
//        try {
//            var characters = PagedResponse<CharacterRickAndMorty>(null)
//
//            apiHelper.getCharactersByQuery(page, type, query, gender, status).collect {
//                characters = it
//            }
//
//
//            val listOfRick = characters.results
//            val endOfPaginationReached = listOfRick.isEmpty()
//            if (loadType == LoadType.REFRESH) {
//                database.deleteAllCharacters()
//                database.deleteAllCharactersKeys()
//            }
//
//            val prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1
//            val nextKey = if (endOfPaginationReached) null else page + 1
//
//            println("!!!!  prevKey $prevKey nextKey $nextKey")
//
//
//            val keys = listOfRick.map {
//                CharacterRemoteKeys(characterId = it.id, prevKey = prevKey, nextKey = nextKey)
//            }
//
//            database.insertAllCharactersKeys(keys)
//
//            database.insertAllCharacters(listOfRick)
//
//
//
//
//            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
//        } catch (exception: IOException) {
//            return MediatorResult.Error(exception)
//        } catch (exception: HttpException) {
//            return MediatorResult.Error(exception)
//        }
//    }
//
//    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, CharacterRickAndMorty>): CharacterRemoteKeys? {
//        // Get the last page that was retrieved, that contained items.
//        // From that last page, get the last item
//        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
//            ?.let { rick ->
//
//                database.getNextPageKeySimple(rick.id)
//            }
//    }
//
//    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, CharacterRickAndMorty>): CharacterRemoteKeys? {
//        // Get the first page that was retrieved, that contained items.
//        // From that first page, get the first item
//        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
//            ?.let { rick ->
//                // Get the remote keys of the first items retrieved
//                database.getNextPageKeySimple(rick.id)
//            }
//    }
//
//    private suspend fun getRemoteKeyClosestToCurrentPosition(
//        state: PagingState<Int, CharacterRickAndMorty>
//    ): CharacterRemoteKeys? {
//        // The paging library is trying to load data after the anchor position
//        // Get the item closest to the anchor position
//        return state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.id?.let { rickId ->
//                database.getNextPageKeySimple(rickId)
//            }
//        }
//    }
//
//}