package com.sahidev.maknyuss.domain.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun instructionsToString(
        instructions: List<Instruction>
    ): String {
        return Gson().toJson(instructions)
    }

    @TypeConverter
    fun stringToInstructions(
        data: String
    ): List<Instruction> {
        val instructions = listOf<Instruction>()
        return Gson().fromJson(data, instructions::class.java)
    }

    @TypeConverter
    fun ingredientsToString(
        ingredients: List<Ingredient>
    ): String {
        return Gson().toJson(ingredients)
    }

    @TypeConverter
    fun stringToIngredients(
        data: String
    ): List<Ingredient> {
        val instructions = listOf<Ingredient>()
        return Gson().fromJson(data, instructions::class.java)
    }
}