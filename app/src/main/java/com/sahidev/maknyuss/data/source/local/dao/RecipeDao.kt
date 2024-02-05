package com.sahidev.maknyuss.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Transaction
import androidx.sqlite.db.SupportSQLiteQuery
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe
import com.sahidev.maknyuss.domain.model.RecipeAndInstructions
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Query(value = "SELECT EXISTS(SELECT * FROM Recipe WHERE id = :id)")
    suspend fun checkRecipe(id: Int): Boolean

    @Transaction
    @Query(value = "SELECT * FROM Recipe WHERE id = :id")
    fun getRecipeInfo(id: Int): Flow<RecipeAndInstructions>

    @Query(value = "SELECT * FROM Recipe")
    fun getSavedRecipes(): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addRecipe(recipe: Recipe)

    @Insert
    suspend fun addInstruction(instruction: Instruction)

    @Query(value = "DELETE FROM Recipe WHERE id = :id")
    suspend fun deleteRecipe(id: Int)
}