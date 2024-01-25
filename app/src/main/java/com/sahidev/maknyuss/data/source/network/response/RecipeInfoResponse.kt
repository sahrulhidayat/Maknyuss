package com.sahidev.maknyuss.data.source.network.response

import com.google.gson.annotations.SerializedName

data class RecipeInfoResponse(

	@field:SerializedName("sustainable")
	val sustainable: Boolean,

	@field:SerializedName("analyzedInstructions")
	val analyzedInstructions: List<AnalyzedInstructionsItem>,

	@field:SerializedName("veryPopular")
	val veryPopular: Boolean,

	@field:SerializedName("healthScore")
	val healthScore: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("diets")
	val diets: List<String>,

	@field:SerializedName("aggregateLikes")
	val aggregateLikes: Int,

	@field:SerializedName("readyInMinutes")
	val readyInMinutes: Int,

	@field:SerializedName("servings")
	val servings: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("preparationMinutes")
	val preparationMinutes: Int,

	@field:SerializedName("summary")
	val summary: String,

	@field:SerializedName("cookingMinutes")
	val cookingMinutes: Int,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("veryHealthy")
	val veryHealthy: Boolean,

	@field:SerializedName("extendedIngredients")
	val extendedIngredients: List<ExtendedIngredientsItem>,

	@field:SerializedName("dishTypes")
	val dishTypes: List<String>,

	@field:SerializedName("weightWatcherSmartPoints")
	val weightWatcherSmartPoints: Int,

	@field:SerializedName("pricePerServing")
	val pricePerServing: Any,

	@field:SerializedName("sourceName")
	val sourceName: String,
)

data class AnalyzedInstructionsItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("steps")
	val steps: List<StepsItem>
)

data class StepsItem(

	@field:SerializedName("number")
	val number: Int,

	@field:SerializedName("ingredients")
	val ingredients: List<IngredientsItem>,

	@field:SerializedName("equipment")
	val equipment: List<EquipmentItem>,

	@field:SerializedName("step")
	val step: String
)

data class IngredientsItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("localizedName")
	val localizedName: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)

data class EquipmentItem(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("localizedName")
	val localizedName: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int
)

data class ExtendedIngredientsItem(

	@field:SerializedName("originalName")
	val originalName: String,

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("amount")
	val amount: Any,

	@field:SerializedName("unit")
	val unit: String,

	@field:SerializedName("measures")
	val measures: Measures,

	@field:SerializedName("nameClean")
	val nameClean: String,

	@field:SerializedName("original")
	val original: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("aisle")
	val aisle: String,

	@field:SerializedName("consistency")
	val consistency: String
)

data class Measures(

	@field:SerializedName("metric")
	val metric: Metric,

	@field:SerializedName("us")
	val us: Us
)

data class Metric(

	@field:SerializedName("amount")
	val amount: Any,

	@field:SerializedName("unitShort")
	val unitShort: String,
)

data class Us(

	@field:SerializedName("amount")
	val amount: Any,

	@field:SerializedName("unitShort")
	val unitShort: String,
)
