package com.example.andersenrickandmortyfinal.data.api


import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("character")
    suspend fun getAllCharactersTest( @Query("page") page: Int): PagedResponse<CharacterRickAndMorty>


    @GET("character")
    suspend fun getPagesOfAllCharacters(
        @Query("page")
        page: Int,
        @Query("gender")
        gender: String,
        @Query("status")
        status: String
    ): PagedResponse<CharacterRickAndMorty>

    @GET("character")
    suspend fun getCharactersByName(
        @Query("page")
        page: Int,
        @Query("name")
        name: String,
        @Query("gender")
        gender: String,
        @Query("status")
        status: String
    ): PagedResponse<CharacterRickAndMorty>

    @GET("character")
    suspend fun getCharactersBySpecies(
        @Query("page")
        page: Int,
        @Query("species")
        species: String,
        @Query("gender")
        gender: String,
        @Query("status")
        status: String
    ): PagedResponse<CharacterRickAndMorty>

    @GET("character")
    suspend fun getCharactersByType(
        @Query("page")
        page: Int,
        @Query("type")
        type: String,
        @Query("gender")
        gender: String,
        @Query("status")
        status: String
    ): PagedResponse<CharacterRickAndMorty>
}

