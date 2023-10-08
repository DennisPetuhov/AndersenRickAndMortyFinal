package com.example.andersenrickandmortyfinal.data.db.characters

import androidx.paging.PagingSource
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import kotlinx.coroutines.flow.Flow

interface DatabaseHelper {
    suspend fun insertAllCharacters(list: List<CharacterRickAndMorty>)
    suspend fun insertAllCharactersKeys(list: List<CharacterRemoteKeys>)


    suspend fun deleteAllCharacters()
    suspend fun deleteAllCharactersKeys()

    fun getNextPageKey(id: Int): Flow<CharacterRemoteKeys?>

    suspend fun getNextPageKeySimple(id: Int): CharacterRemoteKeys?

    fun pagingSource(): PagingSource<Int, CharacterRickAndMorty>

    fun findCharacterByName(
        queryString: String,
        gender: String,
        status: String
    ): PagingSource<Int, CharacterRickAndMorty>

    fun findCharacterBySpecies(
        queryString: String,
        gender: String,
        status: String
    ): PagingSource<Int, CharacterRickAndMorty>

    fun findCharacterByType(
        queryString: String,
        gender: String,
        status: String
    ): PagingSource<Int, CharacterRickAndMorty>

    fun findAllCharacters(
        queryString: String,
        gender: String,
        status: String
    ): PagingSource<Int, CharacterRickAndMorty>


}