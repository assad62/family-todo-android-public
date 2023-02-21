package com.mohammadassad.familytodo.model

import com.google.gson.annotations.SerializedName

data class RegisterUserResponse (
    @SerializedName("email")
    var email : String? = null,
    @SerializedName("familyIdentifier")
    var familyIdentifier : String? = null
)

data class RegisterUserRequest (
    @SerializedName("firstName")
    var firstName : String? = null,
    @SerializedName("lastName")
    var lastName : String? = null,
    @SerializedName("email")
    var email: String? = null,
    @SerializedName("password")
    var password: String? = null,
    @SerializedName("familyIdentifier")
    var familyIdentifier : String? = null,
    @SerializedName("DeviceId")
    var DeviceId:String? = null

)