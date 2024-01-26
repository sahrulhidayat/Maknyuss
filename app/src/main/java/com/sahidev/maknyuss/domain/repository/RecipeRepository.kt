package com.sahidev.maknyuss.domain.repository

import com.sahidev.maknyuss.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun searchRecipe(query: String): Flow<List<Recipe>>
    suspend fun getRecipeInfo(id: Int): Recipe
    suspend fun getRandomRecipe(): Recipe
    fun getSavedRecipes(): Flow<List<Recipe>>
    suspend fun addRecipe(recipe: Recipe)
    suspend fun deleteRecipe(id: Int)
}