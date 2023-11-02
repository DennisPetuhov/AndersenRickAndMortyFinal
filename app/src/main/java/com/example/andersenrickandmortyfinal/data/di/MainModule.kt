package com.example.andersenrickandmortyfinal.data.di

import com.example.andersenrickandmortyfinal.data.db.DatabaseHelper
import com.example.andersenrickandmortyfinal.data.network.api.character.CharacterApiHelper
import com.example.andersenrickandmortyfinal.data.network.api.episode.EpisodeApiHelper
import com.example.andersenrickandmortyfinal.data.network.api.location.LocationApiHelper
import com.example.andersenrickandmortyfinal.data.repository.Repository
import com.example.andersenrickandmortyfinal.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class MainModule {


    @Provides
    @Singleton
    fun provideRepository(
        characterApiHelper: CharacterApiHelper,
        database: DatabaseHelper,
        episodeApiHelper: EpisodeApiHelper,
        locationApiHelper: LocationApiHelper
    ): Repository {
        return RepositoryImpl(
            characterApiHelper,
            database,
            episodeApiHelper,
            locationApiHelper
        )
    }


}