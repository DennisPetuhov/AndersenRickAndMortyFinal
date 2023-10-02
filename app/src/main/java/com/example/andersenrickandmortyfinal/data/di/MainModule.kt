package com.example.andersenrickandmortyfinal.data.di

import android.content.Context
import com.example.andersenrickandmortyfinal.data.api.ApiHelper
import com.example.andersenrickandmortyfinal.data.api.ApiHelperImpl
import com.example.andersenrickandmortyfinal.data.api.ApiService
import com.example.andersenrickandmortyfinal.data.db.characters.DatabaseHelper
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
    fun provideRepository(apiHelper: ApiHelper, database: DatabaseHelper, @ApplicationContext context: Context): Repository {
        return RepositoryImpl(apiHelper, database, context )
    }
}