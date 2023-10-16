package com.example.andersenrickandmortyfinal.data.db.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.andersenrickandmortyfinal.data.model.location.LocationRemoteKeys
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllKeys(characterRemoteKeys: List<LocationRemoteKeys>)

    @Query("SELECT * FROM LOCATION_REMOTE_KEYS_TABLE WHERE locationId LIKE :id")
    fun getNextPageKey(id: Int): Flow<LocationRemoteKeys?>

    @Query("SELECT * FROM LOCATION_REMOTE_KEYS_TABLE WHERE locationId LIKE :id")
    fun getNextPageKeySimple(id: Int): LocationRemoteKeys?


    @Query("DELETE FROM LOCATION_REMOTE_KEYS_TABLE")
    fun deleteAllKey()
}