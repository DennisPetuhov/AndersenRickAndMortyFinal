package com.example.andersenrickandmortyfinal.data.api

import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override suspend fun getAllCharacters(): Flow<PagedResponse<CharacterRickAndMorty>>{
        return flow {
            val response = apiService.getAllCharacters()
            emit(response)
        }
    }

    override suspend fun getPagesOfCharacters(page:Int): Flow<PagedResponse<CharacterRickAndMorty>> {
        return flow {
      val response =  apiService.getPagesOfCharacters(page)
            emit(response)
        }
    }


}