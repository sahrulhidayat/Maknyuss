package com.sahidev.maknyuss.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Search(
    @PrimaryKey
    val id: Int? = null,
    val history: String,
)
