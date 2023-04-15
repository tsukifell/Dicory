package com.cepotdev.dicory.logic.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("loginResult")
    val loginResult: LoginResult? = null,

    @SerializedName("error")
    val error: Boolean,

    @SerializedName("message")
    val message: String?
)

data class LoginResult(

    @SerializedName("name")
    val name: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("token")
    val token: String
)