package com.example.andersenrickandmortyfinal.data.di

import com.example.andersenrickandmortyfinal.data.api.character.CharacterApiHelper
import com.example.andersenrickandmortyfinal.data.api.character.CharacterApiHelperImpl
import com.example.andersenrickandmortyfinal.data.api.character.CharacterService
import com.example.andersenrickandmortyfinal.data.api.episode.EpisodeApiHelperImpl
import com.example.andersenrickandmortyfinal.data.api.episode.EpisodeApiHelper
import com.example.andersenrickandmortyfinal.data.api.episode.EpisodeService
import com.example.andersenrickandmortyfinal.data.api.location.LocationApiHelper
import com.example.andersenrickandmortyfinal.data.api.location.LocationApiHelperImpl
import com.example.andersenrickandmortyfinal.data.api.location.LocationService
import com.example.andersenrickandmortyfinal.domain.Utils
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    }


    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory())
            .build()
    }


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CharacterService {
        return retrofit.create(CharacterService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiHelper(characterService: CharacterService): CharacterApiHelper {
        return CharacterApiHelperImpl(characterService)
    }

    @Provides
    @Singleton
    fun provideEpisodeService(retrofit: Retrofit): EpisodeService {
        return retrofit.create(EpisodeService::class.java)
    }

    @Provides
    @Singleton
    fun provideEpisodeHelper(episodeService: EpisodeService): EpisodeApiHelper {
        return EpisodeApiHelperImpl(episodeService)
    }
    @Provides
    @Singleton
    fun provideLocationService(retrofit: Retrofit): LocationService {
        return retrofit.create(LocationService::class.java)
    }

    @Provides
    @Singleton
    fun provideLocationHelper(locationService:LocationService): LocationApiHelper {
        return LocationApiHelperImpl(locationService)
    }
}