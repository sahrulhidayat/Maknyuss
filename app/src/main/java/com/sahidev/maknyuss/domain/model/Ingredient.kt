package com.sahidev.maknyuss.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

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
data class Ingredient(
    val name: String,
    val nameClean: String,
    val measures: String,
    val image: String,
    @ColumnInfo(index = true)
    val recipeId: Int,
    @PrimaryKey
    val id: Int? = null,
)
