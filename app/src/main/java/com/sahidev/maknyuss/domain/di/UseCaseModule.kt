package com.sahidev.maknyuss.domain.di

import com.sahidev.maknyuss.domain.repository.RecipeRepository
import com.sahidev.maknyuss.domain.usecase.RecipeUseCase
import com.sahidev.maknyuss.domain.usecase.RecipeUseCaseImpl
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
    ) : RecipeUseCase {
        return RecipeUseCaseImpl(repository)
    }
}