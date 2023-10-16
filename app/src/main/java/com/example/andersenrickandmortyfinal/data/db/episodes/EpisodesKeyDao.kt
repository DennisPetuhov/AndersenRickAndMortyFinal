package com.example.andersenrickandmortyfinal.data.db.episodes

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.andersenrickandmortyfinal.data.model.episode.EpisodesRemoteKeys
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodesKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllKeys(characterRemoteKeys : List<EpisodesRemoteKeys>)

    @Query("SELECT * FROM CHARACTER_REMOTE_KEYS_TABLE WHERE characterId LIKE :id")
    fun getNextPageKey(id: Int): Flow<EpisodesRemoteKeys?>
    @Query("SELECT * FROM CHARACTER_REMOTE_KEYS_TABLE WHERE characterId LIKE :id")
    fun getNextPageKeySimple(id: Int): EpisodesRemoteKeys?


    @Query("DELETE FROM CHARACTER_REMOTE_KEYS_TABLE")
    fun deleteAllKey()
}