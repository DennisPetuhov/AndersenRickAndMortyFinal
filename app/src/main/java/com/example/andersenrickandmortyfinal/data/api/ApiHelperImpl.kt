package com.example.andersenrickandmortyfinal.data.api

import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {


//    override suspend fun getAllCharacters(): Flow<PagedResponse<CharacterRickAndMorty>> {
//        return flow {
//            val response = apiService.getAllCharacters()
//            emit(response)
//        }
//    }

    override suspend fun getPagesOfAllCharacters(
        page: Int,
        gender: String,
        status: String
    ): Flow<PagedResponse<CharacterRickAndMorty>> {
        return flow {
            val response = apiService.getPagesOfAllCharacters(page, gender, status)
            emit(response)
        }
    }

    override suspend fun getCharactersByQuery(
        page: Int,
        type: TypeOfRequest,
        query: String,
        gender: String,
        status: String
    ): PagedResponse<CharacterRickAndMorty> {
        return when (type) {


            is TypeOfRequest.None -> {
                println("API $page , $gender, $status")
                apiService.getPagesOfAllCharacters(page, gender, status)
//                        apiService.getAllCharactersTest(page)

            }

            is TypeOfRequest.Name -> {
                apiService.getCharactersByName(page, query, gender, status)
            }

            is TypeOfRequest.Species -> {
                apiService.getCharactersBySpecies(page, query, gender, status)
            }

            is TypeOfRequest.Type -> {
                apiService.getCharactersByType(page, query, gender, status)
            }
        }

    }

}



