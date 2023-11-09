package com.example.andersenrickandmortyfinal.data.network.api.character

import com.example.andersenrickandmortyfinal.data.model.character.CharacterPojo
import com.example.andersenrickandmortyfinal.data.model.main.PagedResponse
import com.example.andersenrickandmortyfinal.data.model.main.TypeOfRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CharacterApiHelperImpl @Inject constructor(private val characterService: CharacterService) :
    CharacterApiHelper {


    override suspend fun getPagesOfAllCharacters(
        page: Int,
        gender: String,
        status: String
    ): Flow<PagedResponse<CharacterPojo>> {
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
    ): Flow<PagedResponse<CharacterPojo>> {
        return flow {
            val response = when (type) {


                is TypeOfRequest.None -> {

                    characterService.getPagesOfAllCharacters(page, gender, status)


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

    override suspend fun getListOfCharacters(list: List<Int>): Flow<List<CharacterPojo>> {
        return flow {
            val response = characterService.getListOfCharacters(list)

            emit(response)
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getCharacterById(id: Int): Flow<CharacterPojo> {
        return flow {
            val response = characterService.getCharacterById(id)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }


}





