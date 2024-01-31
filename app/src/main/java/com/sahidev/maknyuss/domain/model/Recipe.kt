package com.sahidev.maknyuss.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

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
    val dishTypes: String? = null,
    val source: String? = null,
    val instructions: List<Instruction> = emptyList(),
    val ingredients: List<Ingredient> = emptyList(),
)

data class Instruction(
    val number: Int,
    val step: String,
    val equipments: List<Equipment>,
    val ingredients: List<Ingredient>,
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
