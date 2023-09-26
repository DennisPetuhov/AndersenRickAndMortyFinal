package com.example.andersenrickandmortyfinal.data.repository

import com.example.andersenrickandmortyfinal.data.api.ApiHelper
import com.example.andersenrickandmortyfinal.data.api.ApiHelperImpl
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val apiHelper: ApiHelper) : Repository {
    override suspend fun getAllCharacters(): Flow<CharacterRickAndMorty> {
        return apiHelper.getAllCharacters()
    }

    override suspend fun getPagesOfCharacters(page: Int): Flow<CharacterRickAndMorty> {
        return apiHelper.getPagesOfCharacters(page)
    }


}