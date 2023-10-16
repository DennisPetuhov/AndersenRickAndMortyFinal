package com.example.andersenrickandmortyfinal.data.api.character

import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow


interface CharacterApiHelper {


    suspend fun getPagesOfAllCharacters(
        page: Int,
        gender: String,
        status: String
    ): Flow<PagedResponse<CharacterRickAndMorty>>

    suspend fun getCharactersByQueryFlow(
        page: Int,
        type: TypeOfRequest,
        query: String,
        gender: String,
        status: String
    ): Flow<PagedResponse<CharacterRickAndMorty>>


}