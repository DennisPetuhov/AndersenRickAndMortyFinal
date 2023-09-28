package com.example.andersenrickandmortyfinal.data.api

import com.example.andersenrickandmortyfinal.data.model.character.ResultRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow


interface ApiHelper {
    suspend fun getAllCharacters(): Flow<PagedResponse<ResultRickAndMorty>>
    suspend fun getPagesOfCharacters(page:Int): Flow<PagedResponse<ResultRickAndMorty>>

}