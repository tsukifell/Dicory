package com.cepotdev.dicory.logic.model

import com.google.gson.annotations.SerializedName

data class PostingStoriesResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
