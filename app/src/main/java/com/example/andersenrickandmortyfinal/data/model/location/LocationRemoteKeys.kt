package com.example.andersenrickandmortyfinal.data.model.location

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.andersenrickandmortyfinal.data.db.characters.Constants.LOCATION_REMOTE_KEYS_TABLE
import com.example.andersenrickandmortyfinal.data.db.characters.Constants.LOCATION_TABLE

@Entity(tableName = LOCATION_REMOTE_KEYS_TABLE)
data class LocationRemoteKeys(
    @PrimaryKey val locationId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
