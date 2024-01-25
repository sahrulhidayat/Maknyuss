package com.sahidev.maknyuss.domain.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

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
    @ColumnInfo(index = true)
    val recipeId: Int,
    @PrimaryKey
    val id: Int? = null,
)

data class CompleteInstruction(
    @Embedded
    val instruction: Instruction,
    @Relation(
        parentColumn = "id",
        entityColumn = "instructionId",
    )
    val equipments: List<Equipment>,
)
