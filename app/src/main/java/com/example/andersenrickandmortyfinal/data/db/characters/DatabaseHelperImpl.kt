package com.example.andersenrickandmortyfinal.data.db.characters

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseHelperImpl @Inject constructor(private val characterDatabase: CharacterDatabase) :
    DatabaseHelper {
    override suspend fun insertAllCharacters(list: List<CharacterRickAndMorty>) {
        return characterDatabase.withTransaction {   characterDatabase.characterDao().insertAll(list)}

    }

    override suspend fun insertAllCharactersKeys(list: List<CharacterRemoteKeys>) {
        return characterDatabase.characterKeyDao().insertAllCharacterKeys(list)


    }



    override  suspend fun deleteAllCharacters() {
        return characterDatabase.withTransaction {  characterDatabase.characterDao().deleteAllCharacters()}


    }

    override suspend fun deleteAllCharactersKeys() {
        return characterDatabase.withTransaction { characterDatabase.characterKeyDao().deleteAllCharactersKey()}


    }

    override fun getNextPageKey(id: Int): Flow<CharacterRemoteKeys?> {
        return characterDatabase.characterKeyDao().getNextPageKey(id)
    }

    override suspend fun getNextPageKeySimple(id: Int): CharacterRemoteKeys? {
        return characterDatabase.characterKeyDao().getNextPageKeySimple(id)
    }

    override fun pagingSource(): PagingSource<Int, CharacterRickAndMorty> {
        return characterDatabase.characterDao().pagingSource()
    }

    override fun findCharacterByName(queryString: String): PagingSource<Int, CharacterRickAndMorty> {
        return  characterDatabase.characterDao().findCharacterByName(queryString)
    }


}