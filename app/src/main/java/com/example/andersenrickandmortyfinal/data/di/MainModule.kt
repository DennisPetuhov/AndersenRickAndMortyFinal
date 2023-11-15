package com.example.andersenrickandmortyfinal.data.di

import com.example.andersenrickandmortyfinal.data.repository.Repository
import com.example.andersenrickandmortyfinal.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
abstract class MainModule {

    @Binds
    @Named("RepositoryOneQualifier")
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): Repository



}