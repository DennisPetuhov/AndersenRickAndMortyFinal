package com.example.andersenrickandmortyfinal.data.network.api.character

import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow


interface CharacterApiHelper {


    suspend fun getPagesOfAllCharacters(
        page: Int,
        gender: String,
        status: String
    ): Flow<PagedResponse<Character>>

    suspend fun getCharactersByQueryFlow(
        page: Int,
        type: TypeOfRequest,
        query: String,
        gender: String,
        status: String
    ): Flow<PagedResponse<Character>>
 suspend fun  getListOfCharacters(list:List<Int>):Flow<List<Character>>

}