package com.sahidev.maknyuss.domain.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun instructionsToEquipment(
        equipments: List<Equipment>
    ): String {
        return Gson().toJson(equipments)
    }

    @TypeConverter
    fun stringToInstructions(
        data: String
    ): List<Equipment> {
        val equipments = listOf<Equipment>()
        return Gson().fromJson(data, equipments::class.java)
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