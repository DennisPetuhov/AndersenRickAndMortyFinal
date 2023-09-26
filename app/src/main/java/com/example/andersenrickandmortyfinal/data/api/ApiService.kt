package com.example.andersenrickandmortyfinal.data.api


import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {
    @GET("character")
    suspend fun getAllCharacters(): CharacterRickAndMorty


    @GET("character")
    suspend fun getPagesOfCharacters(@Query("page") page: Int): CharacterRickAndMorty

//    @GET("character")
//    suspend fun getPagesOfCharacters1(@Query("page") page: Int): Response<CharacterRickAndMorty>
}

