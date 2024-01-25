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
    val image: String,
    val summary: String? = null,
    val price: String? = null,
    val likes: String? = null,
    val veryPopular: Boolean = false,
    val veryHealthy: Boolean = false,
    val readyMinutes: Int? = null,
    val servings: Int? = null,
    val cuisines: String? = null,
    val dishType: String? = null,
    val source: String? = null,
    var isSaved: Boolean = false
)

data class CompleteRecipe(
    @Embedded
    val recipe: Recipe,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId",
    )
    val ingredients: List<Ingredient>,
    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId",
    )
    val instructions: List<Instruction>,
)