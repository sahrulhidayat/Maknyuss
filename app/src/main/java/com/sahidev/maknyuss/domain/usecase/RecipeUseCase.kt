package com.sahidev.maknyuss.domain.usecase

import com.sahidev.maknyuss.domain.Resource
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import com.sahidev.maknyuss.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeUseCaseImpl @Inject constructor(
    private val repository: RecipeRepository
) : RecipeUseCase {
    override fun searchRecipe(query: String, offset: Int): Flow<Resource<List<Recipe>>> =
        repository.searchRecipe(query, offset)

    override suspend fun getRecipeInfo(id: Int): Flow<Resource<RecipeAndInstructions>> =
        repository.getRecipeInfo(id)

    override suspend fun getRandomRecipe(): Flow<Resource<List<Recipe>>> =
        repository.getRandomRecipe()

    override suspend fun getPriceBreakDown(
        recipeAndInstructions: RecipeAndInstructions
    ): Flow<Resource<RecipeAndInstructions>> {
        return repository.getPriceBreakDown(recipeAndInstructions)
    }

    override fun getSavedRecipes(): Flow<Resource<List<Recipe>>> =
        repository.getSavedRecipes()

    override suspend fun addRecipe(recipe: Recipe) =
        repository.addRecipe(recipe)

    override suspend fun deleteRecipe(recipe: Recipe) =
        repository.deleteRecipe(recipe)

    override suspend fun addInstruction(instruction: Instruction) =
        repository.addInstruction(instruction)
}

interface RecipeUseCase {
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