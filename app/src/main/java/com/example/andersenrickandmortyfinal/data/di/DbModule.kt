package com.example.andersenrickandmortyfinal.data.di

import android.content.Context
import androidx.room.Room
import com.example.andersenrickandmortyfinal.data.db.characters.CharacterDatabase
import com.example.andersenrickandmortyfinal.data.db.characters.Constants.CHARACTER_DATABASE
import com.example.andersenrickandmortyfinal.data.db.characters.DatabaseHelper
import com.example.andersenrickandmortyfinal.data.db.characters.DatabaseHelperImpl
import com.example.andersenrickandmortyfinal.data.model.character.CharacterRickAndMorty
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {
    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): CharacterDatabase {
        return Room.databaseBuilder(
            context, CharacterDatabase::class.java, CHARACTER_DATABASE
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideCharacterDao(db: CharacterDatabase) = db.characterDao()

    @Provides
    @Singleton
    fun provideCharacterKeyDao(db: CharacterDatabase) = db.characterKeyDao()

    @Provides
    fun provideCharacterEntity() = CharacterRickAndMorty()

    @Provides
    @Singleton

    fun provideDataBaseHelper(db: CharacterDatabase): DatabaseHelper {
        return DatabaseHelperImpl(db)
    }
}