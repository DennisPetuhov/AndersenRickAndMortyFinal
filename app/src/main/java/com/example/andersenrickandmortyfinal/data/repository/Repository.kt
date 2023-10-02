package com.example.andersenrickandmortyfinal.data.repository

import androidx.paging.PagingData
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
//     fun getAllCharacters(): Flow<PagedResponse<CharacterRickAndMorty>>
    suspend fun getPagesOfCharacters(page: Int): Flow<PagedResponse<CharacterRickAndMorty>>


    fun getNextPageKey(id: Int): Flow<CharacterRemoteKeys?>

    fun getAllCharactersForMediator(query:String): Flow<PagingData<CharacterRickAndMorty>>






}