package com.sahidev.maknyuss.data.source.network.response

import com.google.gson.annotations.SerializedName

data class RecipesResponse(

	@field:SerializedName("recipes")
	val recipes: List<RecipesItem>
)

data class RecipesItem(

	@field:SerializedName("sustainable")
	val sustainable: Boolean,

	@field:SerializedName("glutenFree")
	val glutenFree: Boolean,

	@field:SerializedName("veryPopular")
	val veryPopular: Boolean,

	@field:SerializedName("healthScore")
	val healthScore: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("diets")
	val diets: List<Any>,

	@field:SerializedName("aggregateLikes")
	val aggregateLikes: Int,

	@field:SerializedName("creditsText")
	val creditsText: String,

	@field:SerializedName("readyInMinutes")
	val readyInMinutes: Int,

	@field:SerializedName("sourceUrl")
	val sourceUrl: String,

	@field:SerializedName("dairyFree")
	val dairyFree: Boolean,

	@field:SerializedName("servings")
	val servings: Int,

	@field:SerializedName("vegetarian")
	val vegetarian: Boolean,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("preparationMinutes")
	val preparationMinutes: Int,

	@field:SerializedName("imageType")
	val imageType: String,

	@field:SerializedName("summary")
	val summary: String,

	@field:SerializedName("cookingMinutes")
	val cookingMinutes: Int,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("veryHealthy")
	val veryHealthy: Boolean,

	@field:SerializedName("vegan")
	val vegan: Boolean,

	@field:SerializedName("cheap")
	val cheap: Boolean,

	@field:SerializedName("cuisines")
	val cuisines: List<String>,

	@field:SerializedName("lowFodmap")
	val lowFodmap: Boolean,

	@field:SerializedName("pricePerServing")
	val pricePerServing: Any,

	@field:SerializedName("spoonacularScore")
	val spoonacularScore: Any,

	@field:SerializedName("sourceName")
	val sourceName: String,
)
