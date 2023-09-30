package com.example.andersenrickandmortyfinal.data.model.character

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.andersenrickandmortyfinal.data.db.characters.Constants.REMOTE_KEYS_TABLE

@Entity(tableName = REMOTE_KEYS_TABLE)
data class CharacterRemoteKeys(
    @PrimaryKey val characterId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)