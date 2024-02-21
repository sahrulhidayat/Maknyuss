package com.sahidev.maknyuss.domain.repository

import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun searchRecipe(query: String, offset: Int): Flow<Resource<List<Recipe>>>
    suspend fun getRecipeInfo(id: Int): Flow<Resource<RecipeAndInstructions>>
    suspend fun getRandomRecipe(): Flow<Resource<List<Recipe>>>
    suspend fun getPriceBreakDown(
        recipeAndInstructions: RecipeAndInstructions
    ): Flow<Resource<RecipeAndInstructions>>

    fun getSavedRecipes(): Flow<Resource<List<Recipe>>>
    suspend fun addRecipe(recipe: Recipe)
    suspend fun deleteRecipe(recipe: Recipe)
    suspend fun addInstruction(instruction: Instruction)
}