package com.example.andersenrickandmortyfinal.data.di

import android.content.Context
import androidx.room.Room
import com.example.andersenrickandmortyfinal.data.db.MainDatabase
import com.example.andersenrickandmortyfinal.data.db.characters.Constants.CHARACTER_DATABASE
import com.example.andersenrickandmortyfinal.data.db.DatabaseHelper
import com.example.andersenrickandmortyfinal.data.db.DatabaseHelperImpl
import com.example.andersenrickandmortyfinal.data.model.character.Character
import com.example.andersenrickandmortyfinal.data.model.episode.Episode
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
    fun provideDB(@ApplicationContext context: Context): MainDatabase {
        return Room.databaseBuilder(
            context, MainDatabase::class.java, CHARACTER_DATABASE
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideCharacterDao(db: MainDatabase) = db.characterDao()

    @Provides
    @Singleton
    fun provideCharacterKeyDao(db: MainDatabase) = db.characterKeyDao()

    @Provides
    fun provideCharacterEntity() = Character()

    @Provides
    @Singleton

    fun provideDataBaseHelper(db: MainDatabase): DatabaseHelper {
        return DatabaseHelperImpl(db)
    }



    @Provides
    @Singleton
    fun provideEpisodesDao(db: MainDatabase) = db.episodeDao()

    @Provides
    @Singleton
    fun provideEpisodesKeyDao(db: MainDatabase) = db.episodeKeyDao()

    @Provides
    fun provideEpisodeEntity() = Episode()

    @Provides
    @Singleton
    fun provideLocationDao(db: MainDatabase) = db.locationDao()

    @Provides
    @Singleton
    fun provideLocationKeyDao(db: MainDatabase) = db.locationKeyDao()

//    @Provides
//    fun provideLocationEntity() = LocationRick()
}