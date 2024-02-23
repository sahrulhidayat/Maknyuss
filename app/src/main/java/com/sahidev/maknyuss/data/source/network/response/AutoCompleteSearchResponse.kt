package com.sahidev.maknyuss.data.source.network.response

import com.google.gson.annotations.SerializedName

data class AutoCompleteResponseItem(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("imageType")
	val imageType: String,
)
