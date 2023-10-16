package com.example.andersenrickandmortyfinal.data.di

import android.content.Context
import com.example.andersenrickandmortyfinal.data.api.character.CharacterApiHelper
import com.example.andersenrickandmortyfinal.data.api.episode.EpisodeApiHelper
import com.example.andersenrickandmortyfinal.data.api.location.LocationApiHelper
import com.example.andersenrickandmortyfinal.data.db.DatabaseHelper
import com.example.andersenrickandmortyfinal.data.repository.Repository
import com.example.andersenrickandmortyfinal.data.repository.RepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
        @ApplicationContext context: Context,
        episodeApiHelper: EpisodeApiHelper,
        locationApiHelper: LocationApiHelper
    ): Repository {
        return RepositoryImpl(
            characterApiHelper,
            database,
            context,
            episodeApiHelper,
            locationApiHelper
        )
    }
}