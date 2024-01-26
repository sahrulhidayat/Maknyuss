package com.sahidev.maknyuss.data.source.local

import com.sahidev.maknyuss.data.source.local.dao.EquipmentDao
import com.sahidev.maknyuss.data.source.local.dao.IngredientDao
import com.sahidev.maknyuss.data.source.local.dao.InstructionDao
import com.sahidev.maknyuss.data.source.local.dao.RecipeDao
import com.sahidev.maknyuss.data.utils.QueryUtils
import com.sahidev.maknyuss.domain.model.Equipment
import com.sahidev.maknyuss.domain.model.Ingredient
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val recipeDao: RecipeDao,
    private val instructionDao: InstructionDao,
    private val ingredientDao: IngredientDao,
    private val equipmentDao: EquipmentDao,
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

    suspend fun addInstruction(instruction: Instruction) =
        instructionDao.addInstruction(instruction)

    suspend fun addIngredient(ingredient: Ingredient) =
        ingredientDao.addIngredient(ingredient)

    suspend fun addEquipment(equipment: Equipment) =
        equipmentDao.addEquipment(equipment)
}