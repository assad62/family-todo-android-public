package com.mohammadassad.familytodo.model

import com.google.gson.annotations.SerializedName

data class LoginUserResponse(
    @SerializedName("email")
    val email:String,
    @SerializedName("token")
    val token:String
)