package com.example.andersenrickandmortyfinal.data.api

import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import kotlinx.coroutines.flow.Flow


interface ApiHelper {
    suspend fun getAllCharacters(): Flow<CharacterRickAndMorty>
    suspend fun getPagesOfCharacters(page:Int): Flow<CharacterRickAndMorty>

}