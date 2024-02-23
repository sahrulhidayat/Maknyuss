package com.sahidev.maknyuss.data.source.network.response

import com.google.gson.annotations.SerializedName

data class RecipeInfoResponse(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("imageType")
	val imageType: String,

	@field:SerializedName("summary")
	val summary: String,

	@field:SerializedName("pricePerServing")
	val pricePerServing: Double,

	@field:SerializedName("aggregateLikes")
	val likes: Int,

	@field:SerializedName("readyInMinutes")
	val readyMinutes: Int,

	@field:SerializedName("servings")
	val servings: Int,

	@field:SerializedName("dishTypes")
	val dishTypes: List<String>,

	@field:SerializedName("diets")
	val diets: List<String>,

	@field:SerializedName("extendedIngredients")
	val extendedIngredients: List<ExtendedIngredientsItem>,

	@field:SerializedName("analyzedInstructions")
	val analyzedInstructions: List<AnalyzedInstructionsItem>,
)

data class ExtendedIngredientsItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("measures")
	val measures: Measures,

	@field:SerializedName("nameClean")
	val nameClean: String,

	@field:SerializedName("name")
	val name: String,
)

data class AnalyzedInstructionsItem(

	@field:SerializedName("steps")
	val steps: List<StepsItem>
)

data class StepsItem(

	@field:SerializedName("number")
	val number: Int,

	@field:SerializedName("ingredients")
	val ingredients: List<IngredientsItem>,

	@field:SerializedName("equipment")
	val equipments: List<EquipmentItem>,

	@field:SerializedName("step")
	val step: String
)

data class IngredientsItem(

	@field:SerializedName("image")
	val image: String?,

	@field:SerializedName("name")
	val name: String,
)

data class EquipmentItem(

	@field:SerializedName("image")
	val image: String?,

	@field:SerializedName("name")
	val name: String,
)

data class Measures(

	@field:SerializedName("metric")
	val metric: Metric,

	@field:SerializedName("us")
	val us: Us
)

data class Metric(

	@field:SerializedName("amount")
	val amount: Double,

	@field:SerializedName("unitShort")
	val unitShort: String,
)

data class Us(

	@field:SerializedName("amount")
	val amount: Double,

	@field:SerializedName("unitShort")
	val unitShort: String,
)
