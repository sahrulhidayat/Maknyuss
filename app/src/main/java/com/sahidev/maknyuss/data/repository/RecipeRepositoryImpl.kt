package com.sahidev.maknyuss.data.repository

import com.sahidev.maknyuss.data.source.local.LocalDataSource
import com.sahidev.maknyuss.data.source.network.RemoteDataSource
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : RecipeRepository {
    override fun searchRecipe(query: String): Flow<List<Recipe>> {
        TODO("Not yet implemented")
    }

    override suspend fun getRecipeInfo(id: Int): Recipe {
        TODO("Not yet implemented")
    }

    override suspend fun getRandomRecipe(): Recipe {
        TODO("Not yet implemented")
    }

    override fun getSavedRecipes(): Flow<List<Recipe>> {
        TODO("Not yet implemented")
    }

    override suspend fun addRecipe(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecipe(id: Int) {
        TODO("Not yet implemented")
    }

}