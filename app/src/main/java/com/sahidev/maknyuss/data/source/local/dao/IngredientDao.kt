package com.sahidev.maknyuss.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.sahidev.maknyuss.domain.model.Ingredient

@Dao
interface IngredientDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addIngredient(ingredient: Ingredient)
}