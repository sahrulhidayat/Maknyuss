package com.sahidev.maknyuss.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sahidev.maknyuss.data.source.local.dao.EquipmentDao
import com.sahidev.maknyuss.data.source.local.dao.IngredientDao
import com.sahidev.maknyuss.data.source.local.dao.InstructionDao
import com.sahidev.maknyuss.data.source.local.dao.RecipeDao
import com.sahidev.maknyuss.domain.model.Equipment
import com.sahidev.maknyuss.domain.model.Ingredient
import com.sahidev.maknyuss.domain.model.Instruction
import com.sahidev.maknyuss.domain.model.Recipe

@Database(
    entities = [Recipe::class, Instruction::class, Ingredient::class, Equipment::class],
    version = 1,
    exportSchema = true
)
abstract class RecipeDatabase : RoomDatabase() {

    abstract val recipeDao: RecipeDao
    abstract val instructionDao: InstructionDao
    abstract val ingredientDao: IngredientDao
    abstract val equipmentDao: EquipmentDao

    companion object {
        const val DATABASE_NAME = "recipe_db"
    }
}