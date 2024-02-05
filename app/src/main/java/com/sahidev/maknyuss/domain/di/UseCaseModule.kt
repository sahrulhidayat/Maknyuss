package com.sahidev.maknyuss.domain.di

import com.sahidev.maknyuss.domain.repository.RecipeRepository
import com.sahidev.maknyuss.domain.repository.SearchRepository
import com.sahidev.maknyuss.domain.usecase.RecipeUseCase
import com.sahidev.maknyuss.domain.usecase.RecipeUseCaseImpl
import com.sahidev.maknyuss.domain.usecase.SearchUseCase
import com.sahidev.maknyuss.domain.usecase.SearchUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideRecipeUseCase(
        repository: RecipeRepository
    ): RecipeUseCase {
        return RecipeUseCaseImpl(repository)
    }

    @Provides
    @Singleton
    fun provideSearchUseCase(
        repository: SearchRepository
    ): SearchUseCase {
        return SearchUseCaseImpl(repository)
    }
}