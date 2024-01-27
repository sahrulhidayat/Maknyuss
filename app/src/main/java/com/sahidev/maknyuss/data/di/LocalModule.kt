package com.sahidev.maknyuss.data.di

import android.content.Context
import androidx.room.Room
import com.sahidev.maknyuss.data.repository.EquipmentRepositoryImpl
import com.sahidev.maknyuss.data.repository.IngredientRepositoryImpl
import com.sahidev.maknyuss.data.repository.InstructionRepositoryImpl
import com.sahidev.maknyuss.data.source.local.LocalDataSource
import com.sahidev.maknyuss.data.source.local.RecipeDatabase
import com.sahidev.maknyuss.domain.repository.EquipmentRepository
import com.sahidev.maknyuss.domain.repository.IngredientRepository
import com.sahidev.maknyuss.domain.repository.InstructionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideRecipeDatabase(
        @ApplicationContext context: Context
    ): RecipeDatabase {
        return Room.databaseBuilder(
            context,
            RecipeDatabase::class.java,
            RecipeDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(db: RecipeDatabase): LocalDataSource {
        return LocalDataSource(
            db.recipeDao,
            db.instructionDao,
            db.ingredientDao,
            db.equipmentDao
        )
    }

    @Provides
    @Singleton
    fun provideInstructionRepository(
        localDataSource: LocalDataSource
    ): InstructionRepository {
        return InstructionRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideIngredientRepository(
        localDataSource: LocalDataSource
    ): IngredientRepository {
        return IngredientRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideEquipmentRepository(
        localDataSource: LocalDataSource
    ): EquipmentRepository {
        return EquipmentRepositoryImpl(localDataSource)
    }
}