package com.cepotdev.dicory.logic.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("error") val error : Boolean,
    @SerializedName("message") val message: String
)