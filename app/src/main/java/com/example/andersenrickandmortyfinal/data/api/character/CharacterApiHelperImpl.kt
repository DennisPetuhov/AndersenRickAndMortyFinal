package com.example.andersenrickandmortyfinal.data.api.character

import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import com.example.andersenrickandmortyfinal.data.model.character.TypeOfRequest
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterApiHelperImpl @Inject constructor(private val characterService: CharacterService) :
    CharacterApiHelper {


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
            val response =
                characterService.getPagesOfAllCharacters(page, gender, status)
            emit(response)
        }
    }


    override suspend fun getCharactersByQueryFlow(
        page: Int,
        type: TypeOfRequest,
        query: String,
        gender: String,
        status: String
    ): Flow<PagedResponse<CharacterRickAndMorty>> {
        return flow {
            val response = when (type) {


                is TypeOfRequest.None -> {
                    println("API FLOW  $page , $gender, $status")
                    characterService.getPagesOfAllCharacters(page, gender, status)
//                        apiService.getAllCharactersTest(page)

                }

                is TypeOfRequest.Name -> {
                    characterService.getCharactersByName(page, query, gender, status)
                }

                is TypeOfRequest.Species -> {
                    characterService.getCharactersBySpecies(page, query, gender, status)
                }

                is TypeOfRequest.Type -> {
                    characterService.getCharactersByType(page, query, gender, status)
                }

                is TypeOfRequest.Code -> {

                    characterService.getPagesOfAllCharacters(page, gender, status)
//

                }

                else -> {
                    characterService.getPagesOfAllCharacters(page, gender, status)
                }
            }
            emit(response)
        }.flowOn(Dispatchers.IO)
    }






}





