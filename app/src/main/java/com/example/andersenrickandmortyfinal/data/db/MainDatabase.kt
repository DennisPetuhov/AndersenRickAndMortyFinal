package com.example.andersenrickandmortyfinal.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.andersenrickandmortyfinal.data.db.characters.CharacterDao
import com.example.andersenrickandmortyfinal.data.db.characters.CharacterKeysDao
import com.example.andersenrickandmortyfinal.data.db.episodes.EpisodesDao
import com.example.andersenrickandmortyfinal.data.db.episodes.EpisodesKeyDao
import com.example.andersenrickandmortyfinal.data.db.location.LocationDao
import com.example.andersenrickandmortyfinal.data.db.location.LocationKeysDao
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
import com.example.andersenrickandmortyfinal.data.model.episode.EpisodesRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.location.LocationRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.location.LocationRick


@Database(
    entities =
    [Character::class, CharacterRemoteKeys::class,
        EpisodesRemoteKeys::class, Episode::class,
    LocationRick::class, LocationRemoteKeys::class],
    version = 5
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterKeyDao(): CharacterKeysDao
    abstract fun episodeDao(): EpisodesDao
    abstract fun episodeKeyDao(): EpisodesKeyDao
    abstract fun locationKeyDao():LocationKeysDao
    abstract fun locationDao():LocationDao
}