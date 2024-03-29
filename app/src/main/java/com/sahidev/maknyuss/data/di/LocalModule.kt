package com.sahidev.maknyuss.data.di

import android.content.Context
import androidx.room.Room
import com.sahidev.maknyuss.data.source.local.LocalDataSource
import com.sahidev.maknyuss.data.source.local.RecipeDatabase
import com.sahidev.maknyuss.data.source.local.media.ImagesMediaProvider
import com.sahidev.maknyuss.data.source.local.media.ImagesMediaProviderImpl
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
    fun provideContext(
        @ApplicationContext context: Context,
    ): Context {
        return context
    }

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
            db.searchDao,
        )
    }

    @Provides
    @Singleton
    fun provideImageContentProvider(
        @ApplicationContext context: Context
    ): ImagesMediaProvider {
        return ImagesMediaProviderImpl(context)
    }
}