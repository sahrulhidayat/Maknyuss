package com.sahidev.maknyuss.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Transaction
    @Query(value = "SELECT * FROM Recipe WHERE id = :id")
    fun getRecipeInfo(id: Int): Flow<RecipeAndInstructions>

    @Query(value = "SELECT * FROM Recipe")
    fun getSavedRecipes(): Flow<List<Recipe>>

    @Upsert
    suspend fun addRecipe(recipe: Recipe)

    @Insert
    suspend fun addInstruction(instruction: Instruction)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
}