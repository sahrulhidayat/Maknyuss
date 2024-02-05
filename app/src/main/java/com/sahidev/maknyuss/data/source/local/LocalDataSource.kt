package com.sahidev.maknyuss.data.source.local

import com.sahidev.maknyuss.data.source.local.dao.RecipeDao
import com.sahidev.maknyuss.data.source.local.dao.SearchDao
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import com.sahidev.maknyuss.domain.model.Search
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao,
    private val searchDao: SearchDao,
) {
    suspend fun checkRecipe(id: Int): Boolean {
        return recipeDao.checkRecipe(id)
    }

    fun getRecipeInfo(id: Int): Flow<RecipeAndInstructions> = recipeDao.getRecipeInfo(id)

    fun getSavedRecipes(): Flow<List<Recipe>> = recipeDao.getSavedRecipes()

    suspend fun addRecipe(recipe: Recipe) = recipeDao.addRecipe(recipe)

    suspend fun addInstruction(instruction: Instruction) = recipeDao.addInstruction(instruction)

    suspend fun deleteRecipe(id: Int) = recipeDao.deleteRecipe(id)

    suspend fun addSearchHistory(search: Search) = searchDao.addSearchHistory(search)

    fun getSearchHistory(): Flow<List<Search>> = searchDao.getSearchHistory()

    suspend fun deleteSearchHistory(query: String) = searchDao.deleteSearchHistory(query)

    suspend fun clearSearchHistory() = searchDao.clearSearchHistory()
}