package com.example.andersenrickandmortyfinal.data.db.characters

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRemoteKeys
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty


@Database(
    entities =
    [CharacterRickAndMorty::class, CharacterRemoteKeys::class],
    version = 1
)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun characterKeyDao(): CharacterKeysDao
}