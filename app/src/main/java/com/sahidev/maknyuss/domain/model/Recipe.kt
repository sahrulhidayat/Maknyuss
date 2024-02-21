package com.sahidev.maknyuss.domain.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Recipe(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String? = null,
    val pricePerServing: String? = null,
    val totalCost: String? = null,
    val summary: String? = null,
    val likes: String? = null,
    val readyMinutes: Int? = null,
    val servings: Int? = null,
    val dishTypes: List<String> = emptyList(),
    val diets: List<String> = emptyList(),
    val equipments: List<Equipment> = emptyList(),
    val ingredients: List<Ingredient> = emptyList(),
    val priceBreakDown: List<Price> = emptyList(),
    val favorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

data class RecipeAndInstructions(
    @Embedded
    val recipe: Recipe,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val instructions: List<Instruction>
)

data class Equipment(
    val name: String,
    val image: String,
)

data class Ingredient(
    val name: String,
    val image: String,
    val nameClean: String? = null,
    val amountMetric: String? = null,
    val amountUs: String? = null,
)

data class Price(
    val ingredientName: String,
    val price: String,
    val amountMetric: String? = null,
    val amountUs: String? = null,
    val image: String? = null
)