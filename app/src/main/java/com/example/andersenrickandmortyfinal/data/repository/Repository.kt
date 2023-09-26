package com.example.andersenrickandmortyfinal.data.repository

import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import kotlinx.coroutines.flow.Flow

interface Repository  {
    suspend fun getAllCharacters(): Flow<CharacterRickAndMorty>
    suspend fun getPagesOfCharacters(page:Int): Flow<CharacterRickAndMorty>
}