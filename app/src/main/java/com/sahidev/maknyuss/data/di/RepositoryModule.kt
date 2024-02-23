package com.sahidev.maknyuss.data.di

import com.sahidev.maknyuss.data.repository.RecipeRepositoryImpl
import com.sahidev.maknyuss.data.repository.SearchRepositoryImpl
import com.sahidev.maknyuss.data.source.local.LocalDataSource
import com.sahidev.maknyuss.data.source.network.RemoteDataSource
import com.sahidev.maknyuss.domain.repository.RecipeRepository
import com.sahidev.maknyuss.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRecipeRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): RecipeRepository {
        return RecipeRepositoryImpl(localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource
    ): SearchRepository {
        return SearchRepositoryImpl(localDataSource, remoteDataSource)
    }
}