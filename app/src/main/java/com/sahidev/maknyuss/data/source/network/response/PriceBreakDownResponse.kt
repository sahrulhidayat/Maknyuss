package com.sahidev.maknyuss.data.source.network.response

import com.google.gson.annotations.SerializedName

data class PriceBreakDownResponse(

	@field:SerializedName("totalCostPerServing")
	val totalCostPerServing: Double,

	@field:SerializedName("totalCost")
	val totalCost: Double,

	@field:SerializedName("ingredients")
	val ingredients: List<IngredientsBreakDown>
)

data class IngredientsBreakDown(

	@field:SerializedName("image")
	val image: String,

	@field:SerializedName("amount")
	val amount: Amount,

	@field:SerializedName("price")
	val price: Double,

	@field:SerializedName("name")
	val name: String
)

data class Amount(

	@field:SerializedName("metric")
	val metric: MetricBreakDown,

	@field:SerializedName("us")
	val us: UsBreakDown
)

data class MetricBreakDown(

	@field:SerializedName("unit")
	val unit: String,

	@field:SerializedName("value")
	val value: Double
)

data class UsBreakDown(

	@field:SerializedName("unit")
	val unit: String,

	@field:SerializedName("value")
	val value: Double
)
