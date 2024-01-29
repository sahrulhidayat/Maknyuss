package com.sahidev.maknyuss.domain.repository

import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Equipment
import com.sahidev.maknyuss.domain.model.Ingredient
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    fun searchRecipe(query: String, offset: Int): Flow<Resource<List<Recipe>>>
    suspend fun getRecipeInfo(id: Int): Flow<Resource<Recipe>>
    suspend fun getRandomRecipe(): Flow<Resource<List<Recipe>>>
    fun getSavedRecipes(): Flow<Resource<List<Recipe>>>
    suspend fun addRecipe(recipe: Recipe)
    suspend fun deleteRecipe(id: Int)
    suspend fun addInstruction(instruction: Instruction)
    suspend fun addIngredient(ingredient: Ingredient)
    suspend fun addEquipment(equipment: Equipment)
}