package com.example.andersenrickandmortyfinal.data.db.characters

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllCharacterKeys(characterRemoteKeys: List<CharacterRemoteKeys>)

    @Query("SELECT * FROM CHARACTER_REMOTE_KEYS_TABLE WHERE characterId LIKE :id")
    fun getNextPageKey(id: Int): Flow<CharacterRemoteKeys?>

    @Query("SELECT * FROM CHARACTER_REMOTE_KEYS_TABLE WHERE characterId LIKE :id")
    fun getNextPageKeySimple(id: Int): CharacterRemoteKeys?


    @Query("DELETE FROM CHARACTER_REMOTE_KEYS_TABLE")
    fun deleteAllCharactersKey()
}