package com.example.andersenrickandmortyfinal.data.repository

import com.example.andersenrickandmortyfinal.data.model.character.ResultRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow

interface Repository  {
    suspend fun getAllCharacters(): Flow<PagedResponse<ResultRickAndMorty>>
    suspend fun getPagesOfCharacters(page:Int): Flow<PagedResponse<ResultRickAndMorty>>
}