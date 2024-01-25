package com.sahidev.maknyuss.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Instruction::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("instructionId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Equipment(
    val name: String,
    val image: String,
    @ColumnInfo(index = true)
    val instructionId: Int,
    @PrimaryKey
    val id: Int? = null,
)
