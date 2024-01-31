package com.sahidev.maknyuss.data.source.local

import com.sahidev.maknyuss.data.source.local.dao.RecipeDao
import com.sahidev.maknyuss.data.utils.QueryUtils
import com.sahidev.maknyuss.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao,
) {

    fun searchRecipe(query: String): Flow<List<Recipe>> {
        return recipeDao.searchRecipe(
            QueryUtils.getRecipeQuery(query)
        )
    }

    fun getRecipeInfo(id: Int): Flow<Recipe> = recipeDao.getRecipeInfo(id)

    fun getSavedRecipes(): Flow<List<Recipe>> = recipeDao.getSavedRecipes()

    suspend fun addRecipe(recipe: Recipe) = recipeDao.addRecipe(recipe)

    suspend fun deleteRecipe(id: Int) = recipeDao.deleteRecipe(id)
}