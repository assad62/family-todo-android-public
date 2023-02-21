package com.mohammadassad.familytodo.model

import com.google.gson.annotations.SerializedName

data class UserTask (

    @SerializedName("id")
    var id: String? = null,
    @SerializedName("taskName")
    var taskName: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("familyId")
    var familyId: String? = null,
    @SerializedName("family")
    var family: String? = null,
    @SerializedName("image")
    var image : String? = null,
    @SerializedName("taskStatus")
    var taskStatus : Int?= null,
    @SerializedName("createdBy")
    var createdBy: String? = null,
    @SerializedName("createdDate")
    var createdDate: String? = null,

)