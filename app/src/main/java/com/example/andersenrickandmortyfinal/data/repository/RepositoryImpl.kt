package com.example.andersenrickandmortyfinal.data.repository

import com.example.andersenrickandmortyfinal.data.api.ApiHelper
import com.example.andersenrickandmortyfinal.data.model.character.ResultRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) : Repository {
    override suspend fun getAllCharacters(): Flow<PagedResponse<ResultRickAndMorty>> {
        return apiHelper.getAllCharacters()
    }

    override suspend fun getPagesOfCharacters(page: Int): Flow<PagedResponse<ResultRickAndMorty>> {
        return apiHelper.getPagesOfCharacters(page)
    }


}