package com.example.andersenrickandmortyfinal.data.di

import com.example.andersenrickandmortyfinal.data.api.ApiHelper
import com.example.andersenrickandmortyfinal.data.api.ApiHelperImpl
import com.example.andersenrickandmortyfinal.data.api.ApiService
import com.example.andersenrickandmortyfinal.data.repository.Repository
import com.example.andersenrickandmortyfinal.data.repository.RepositoryImpl
import com.example.andersenrickandmortyfinal.domain.Utils.Companion.BASE_URL
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
class MainModule {
    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
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
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideApiHelper(apiService: ApiService):ApiHelper{
        return ApiHelperImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideRepository(apiHelper:ApiHelper):Repository{
        return RepositoryImpl(apiHelper)
    }
}