package com.sahidev.maknyuss.domain.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Recipe(
    @PrimaryKey
    val id: Int,
    val title: String,
    val image: String? = null,
    val summary: String? = null,
    val price: String? = null,
    val likes: String? = null,
    val readyMinutes: Int? = null,
    val servings: Int? = null,
    val equipments: List<Equipment> = emptyList(),
    val ingredients: List<Ingredient> = emptyList(),
    val favorite: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("recipeId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Instruction(
    val number: Int,
    val step: String,
    val equipments: List<Equipment>,
    val ingredients: List<Ingredient>,
    @ColumnInfo(index = true)
    val recipeId: Int,
    @PrimaryKey
    val id: Int? = null,
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
    val measures: String? = null,
)
