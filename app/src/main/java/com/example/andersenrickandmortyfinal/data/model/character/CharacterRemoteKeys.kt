package com.example.andersenrickandmortyfinal.data.model.character

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.andersenrickandmortyfinal.domain.utils.Constants.CHARACTER_REMOTE_KEYS_TABLE

@Entity(tableName = CHARACTER_REMOTE_KEYS_TABLE)
data class CharacterRemoteKeys(
    @PrimaryKey val characterId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)