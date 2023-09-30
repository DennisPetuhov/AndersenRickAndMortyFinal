package com.example.andersenrickandmortyfinal.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.andersenrickandmortyfinal.data.api.ApiHelper
import com.example.andersenrickandmortyfinal.data.db.characters.DatabaseHelper
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import com.example.andersenrickandmortyfinal.data.paging.CharactersMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiHelper: ApiHelper,
    private val dataBaseCharacter: DatabaseHelper
) : Repository {
//    override suspend fun getAllCharacters(): Flow<PagedResponse<CharacterRickAndMorty>> {
//        return apiHelper.getAllCharacters()
//    }

    override suspend fun getPagesOfCharacters(page: Int): Flow<PagedResponse<CharacterRickAndMorty>> {
        return apiHelper.getPagesOfCharacters(page)
    }


    override fun getNextPageKey(id: Int): Flow<CharacterRemoteKeys?> {
        return dataBaseCharacter.getNextPageKey(id)
    }


    @OptIn(ExperimentalPagingApi::class)
    override fun getAllCharactersForMediator(): Flow<PagingData<CharacterRickAndMorty>> {
        return Pager(
            config = PagingConfig(pageSize = 30, prefetchDistance = 3),
            remoteMediator = CharactersMediator(apiHelper = apiHelper, database = dataBaseCharacter)
        ) { dataBaseCharacter.pagingSource() }.flow

    }


}
