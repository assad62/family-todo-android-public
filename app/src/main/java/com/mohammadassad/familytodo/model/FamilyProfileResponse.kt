package com.mohammadassad.familytodo.model


data class FamilyProfileResponse(
    val name:String,
    val image:String,
    val familyIdentifier:String,
    val listOfFamilyMembers: List<String>,
)
