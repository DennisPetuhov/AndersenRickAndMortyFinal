package com.example.andersenrickandmortyfinal.data.api

import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow


interface ApiHelper {


    suspend fun getPagesOfAllCharacters(page: Int,gender:String,status:String): Flow<PagedResponse<CharacterRickAndMorty>>

    suspend fun getCharactersByQuery(page: Int, type:TypeOfRequest, query: String,gender:String,status:String):PagedResponse<CharacterRickAndMorty>

}