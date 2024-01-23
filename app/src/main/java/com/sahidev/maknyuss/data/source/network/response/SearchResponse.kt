package com.sahidev.maknyuss.data.source.network.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(

	@field:SerializedName("number")
	val number: Int,

	@field:SerializedName("totalResults")
	val totalResults: Int,

	@field:SerializedName("offset")
	val offset: Int,

	@field:SerializedName("results")
	val results: List<ResultsItem>
)

data class ResultsItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("nutrition")
	val nutrition: Nutrition,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("imageType")
	val imageType: String
)

data class Nutrition(

	@field:SerializedName("nutrients")
	val nutrients: List<NutrientsItem>
)

data class NutrientsItem(

	@field:SerializedName("amount")
	val amount: Any,

	@field:SerializedName("unit")
	val unit: String,

	@field:SerializedName("name")
	val name: String
)
