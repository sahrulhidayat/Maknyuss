package com.sahidev.maknyuss.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Search(
    @PrimaryKey
    val query: String,
    val timestamp: Long,
    val image: String? = null,
    val id: Int? = null
)
