package com.sahidev.maknyuss.data.source.network.response

import com.google.gson.annotations.SerializedName

data class RecipeInfoResponse(

	@field:SerializedName("instructions")
	val instructions: String,

	@field:SerializedName("sustainable")
	val sustainable: Boolean,

	@field:SerializedName("analyzedInstructions")
	val analyzedInstructions: List<AnalyzedInstructionsItem>,

	@field:SerializedName("glutenFree")
	val glutenFree: Boolean,

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

	@field:SerializedName("winePairing")
	val winePairing: WinePairing,

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

	@field:SerializedName("extendedIngredients")
	val extendedIngredients: List<ExtendedIngredientsItem>,

	@field:SerializedName("dishTypes")
	val dishTypes: List<String>,

	@field:SerializedName("gaps")
	val gaps: String,

	@field:SerializedName("cuisines")
	val cuisines: List<Any>,

	@field:SerializedName("lowFodmap")
	val lowFodmap: Boolean,

	@field:SerializedName("weightWatcherSmartPoints")
	val weightWatcherSmartPoints: Int,

	@field:SerializedName("occasions")
	val occasions: List<Any>,

	@field:SerializedName("pricePerServing")
	val pricePerServing: Any,

	@field:SerializedName("spoonacularScore")
	val spoonacularScore: Any,

	@field:SerializedName("sourceName")
	val sourceName: String,

	@field:SerializedName("originalId")
	val originalId: Any,

	@field:SerializedName("spoonacularSourceUrl")
	val spoonacularSourceUrl: String
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

data class Measures(

	@field:SerializedName("metric")
	val metric: Metric,

	@field:SerializedName("us")
	val us: Us
)

data class AnalyzedInstructionsItem(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("steps")
	val steps: List<StepsItem>
)

data class WinePairing(

	@field:SerializedName("productMatches")
	val productMatches: List<Any>,

	@field:SerializedName("pairingText")
	val pairingText: String,

	@field:SerializedName("pairedWines")
	val pairedWines: List<Any>
)

data class Metric(

	@field:SerializedName("amount")
	val amount: Any,

	@field:SerializedName("unitShort")
	val unitShort: String,

	@field:SerializedName("unitLong")
	val unitLong: String
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

	@field:SerializedName("meta")
	val meta: List<Any>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("aisle")
	val aisle: String,

	@field:SerializedName("consistency")
	val consistency: String
)

data class Us(

	@field:SerializedName("amount")
	val amount: Any,

	@field:SerializedName("unitShort")
	val unitShort: String,

	@field:SerializedName("unitLong")
	val unitLong: String
)
