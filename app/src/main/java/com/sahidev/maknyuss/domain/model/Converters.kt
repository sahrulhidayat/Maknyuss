package com.sahidev.maknyuss.domain.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun listToString(
        list: List<String>
    ): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(
        data: String
    ): List<String> {
        val list = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(data, list)
    }

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
        val equipments = object : TypeToken<List<Equipment>>() {}.type
        return Gson().fromJson(data, equipments)
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
        val ingredients = object : TypeToken<List<Ingredient>>() {}.type
        return Gson().fromJson(data, ingredients)
    }

    @TypeConverter
    fun priceBreakDownToString(
        priceBreakDown: List<Price>
    ): String {
        return Gson().toJson(priceBreakDown)
    }

    @TypeConverter
    fun stringToPriceBreakDown(
        data: String
    ): List<Price> {
        val priceBreakDown = object : TypeToken<List<Price>>() {}.type
        return Gson().fromJson(data, priceBreakDown)
    }
}