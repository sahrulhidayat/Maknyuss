package com.sahidev.maknyuss.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Upsert
import androidx.sqlite.db.SupportSQLiteQuery
import com.sahidev.maknyuss.domain.model.Recipe
import kotlinx.coroutines.flow.Flow
import retrofit2.http.DELETE

@Dao
interface RecipeDao {
    @RawQuery(observedEntities = [Recipe::class])
    fun searchRecipe(query: SupportSQLiteQuery): Flow<List<Recipe>>

    @Query(value = "SELECT * FROM Recipe WHERE id = :id")
    fun getRecipeInfo(id: Int): Flow<Recipe>

    @Query(value = "SELECT * FROM Recipe")
    fun getSavedRecipes(): Flow<List<Recipe>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addRecipe(recipe: Recipe)

    @Query(value = "DELETE FROM Recipe WHERE id = :id")
    suspend fun deleteRecipe(id: Int)
}