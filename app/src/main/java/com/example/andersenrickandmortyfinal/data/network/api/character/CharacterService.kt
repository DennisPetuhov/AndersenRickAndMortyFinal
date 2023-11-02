package com.example.andersenrickandmortyfinal.data.network.api.character


import com.example.andersenrickandmortyfinal.data.model.character.CharacterPojo
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface CharacterService {



    @GET("character")
    suspend fun getPagesOfAllCharacters(
        @Query("page")
        page: Int,
        @Query("gender")
        gender: String,
        @Query("status")
        status: String
    ): PagedResponse<CharacterPojo>

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
    ): PagedResponse<CharacterPojo>

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
    ): PagedResponse<CharacterPojo>

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
    ): PagedResponse<CharacterPojo>

    @GET("character/{list}")
    suspend fun getListOfCharacters(
        @Path ("list")
    list:List<Int>):List<CharacterPojo>
    @GET("character/{id}")
    suspend fun  getCharacterById(
        @Path("id")
        id:Int):CharacterPojo

}

