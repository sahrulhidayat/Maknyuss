package com.sahidev.maknyuss.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sahidev.maknyuss.data.source.local.dao.RecipeDao
import com.sahidev.maknyuss.domain.model.Converters
import com.sahidev.maknyuss.domain.model.Equipment
import com.sahidev.maknyuss.domain.model.Ingredient
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe

@Database(
    entities = [Recipe::class, Instruction::class, Ingredient::class, Equipment::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract val recipeDao: RecipeDao

    companion object {
        const val DATABASE_NAME = "recipe_db"
    }
}