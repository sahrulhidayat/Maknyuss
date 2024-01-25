package com.sahidev.maknyuss.data.source.network.response

import com.google.gson.annotations.SerializedName

data class RecipesResponse(

    @field:SerializedName("recipes")
    val recipes: List<RecipesItem>
)

data class RecipesItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("image")
    val image: String,
)
