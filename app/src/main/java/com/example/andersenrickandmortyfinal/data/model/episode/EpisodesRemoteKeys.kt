package com.example.andersenrickandmortyfinal.data.model.episode

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.andersenrickandmortyfinal.data.db.characters.Constants.EPISODES_REMOTE_KEYS_TABLE

@Entity(tableName = EPISODES_REMOTE_KEYS_TABLE)

data class EpisodesRemoteKeys(
    @PrimaryKey
    val characterId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
