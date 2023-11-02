package com.example.andersenrickandmortyfinal.data.network.api.character

import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.character.CharacterPojo
import com.example.andersenrickandmortyfinal.data.model.main.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow


interface CharacterApiHelper {


    suspend fun getPagesOfAllCharacters(
        page: Int,
        gender: String,
        status: String
    ): Flow<PagedResponse<CharacterPojo>>

    suspend fun getCharactersByQueryFlow(
        page: Int,
        type: TypeOfRequest,
        query: String,
        gender: String,
        status: String
    ): Flow<PagedResponse<CharacterPojo>>
 suspend fun  getListOfCharacters(list:List<Int>):Flow<List<CharacterPojo>>

    suspend fun  getCharacterById(id:Int):Flow<CharacterPojo>


}