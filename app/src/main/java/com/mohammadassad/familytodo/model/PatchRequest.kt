package com.mohammadassad.familytodo.model



data class PatchRequest(
    var op    : String? = null,
    var path  : String? = null,
    var value : String? = null
)
//data class PatchRequest(
//   val r: List<PatchRequestChild>
//)
