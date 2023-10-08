package com.example.andersenrickandmortyfinal.data.repository

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.andersenrickandmortyfinal.data.api.ApiHelper
import com.example.andersenrickandmortyfinal.data.db.characters.DatabaseHelper
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import com.example.andersenrickandmortyfinal.data.paging.CharactersMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dataBaseCharacter: DatabaseHelper,
    private val context: Context
) : Repository {
    override fun getAllCharacters(): Flow<PagedResponse<CharacterRickAndMorty>> {
        return flow { dataBaseCharacter.pagingSource() }
    }

//    override suspend fun getAllCharacters(): Flow<PagedResponse<CharacterRickAndMorty>> {
//        return apiHelper.getAllCharacters()
//    }

    override suspend fun getPagesOfAllCharacters(
        page: Int, gender: String,
        status: String
    ): Flow<PagedResponse<CharacterRickAndMorty>> {
        return apiHelper.getPagesOfAllCharacters(page, gender, status)
    }


    override fun getNextPageKey(id: Int): Flow<CharacterRemoteKeys?> {
        return dataBaseCharacter.getNextPageKey(id)
    }


    @OptIn(ExperimentalPagingApi::class)
    override fun getAllCharactersFromMediator(
        type: TypeOfRequest,
        query: String,
        gender: String,
        status: String
    ): Flow<PagingData<CharacterRickAndMorty>> {
        Log.d("QUERY SEARCH", "New query: $query + $status + ${type.toString()} + $gender")
        val apiGender = "%20$gender"
        val apiStatus = "%20$status"
        val dbQuery = "%${query.replace(' ', '%')}%"
        val dbGender = "%${query.replace(' ', '%')}%"
        val dbStatus = "%${query.replace(' ', '%')}%"
//
//        val dbGender = "%${query.replace(' ', '.')}%"
//        val dbStatus = "%${query.replace(' ', '.')}%"
        println(dbGender)
        println("REPOSITORYIMPL gender=$gender status =$status")


        return Pager(
            config = PagingConfig(
                pageSize = 30,
                prefetchDistance = 3,
                enablePlaceholders = false
            ),
            remoteMediator = CharactersMediator(
                apiHelper = apiHelper,
                database = dataBaseCharacter,
                context = context,
                query = query,
                type = type,
                gender = gender,
                status = status

            )
        )
        {

            pagingData(type, dbQuery, dbGender, dbStatus)

        }.flow


    }


    private fun pagingData(
        type: TypeOfRequest,
        query: String,
        gender: String,
        status: String
    ): PagingSource<Int, CharacterRickAndMorty> {
        return when (type) {
            is TypeOfRequest.None -> {
                dataBaseCharacter.findAllCharacters(query, gender, status)

            }

            is TypeOfRequest.Name -> {
                dataBaseCharacter.findCharacterByName(query, gender, status)
            }

            is TypeOfRequest.Species -> {
                dataBaseCharacter.findCharacterBySpecies(query, gender, status)
            }

            is TypeOfRequest.Type -> {
                dataBaseCharacter.findCharacterByType(query, gender, status)
            }
        }
    }


}






