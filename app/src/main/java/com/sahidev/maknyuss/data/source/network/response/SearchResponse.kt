package com.sahidev.maknyuss.data.source.network.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

    @field:SerializedName("number")
    val number: Int,

    @field:SerializedName("offset")
    val offset: Int,

    @field:SerializedName("totalResults")
    val totalResults: Int,

    @field:SerializedName("results")
    val results: List<ResultsItem>
)

data class ResultsItem(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("image")
    val image: String?,


    @field:SerializedName("pricePerServing")
    val pricePerServing: Double,

    @field:SerializedName("dishTypes")
    val dishTypes: List<String>,

    @field:SerializedName("diets")
    val diets: List<String>,
)
