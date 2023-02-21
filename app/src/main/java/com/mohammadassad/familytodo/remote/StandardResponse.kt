package com.mohammadassad.familytodo.remote

data class StandardResponse<T>(
    val status: Int,
    val message: String,
    val data: T)