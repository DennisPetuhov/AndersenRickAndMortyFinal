package com.example.andersenrickandmortyfinal.data.api

import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow


interface ApiHelper {
    suspend fun getAllCharacters(): Flow<PagedResponse<CharacterRickAndMorty>>
    suspend fun getPagesOfCharacters(page: Int): Flow<PagedResponse<CharacterRickAndMorty>>

    suspend fun getPagesOfCharactersByQuery(page: Int ,name: String): Flow<PagedResponse<CharacterRickAndMorty>>

}