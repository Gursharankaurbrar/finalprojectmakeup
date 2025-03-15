package com.example.finalprojectmakeup.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductColor(
    @Json(name = "colour_name")
    var colourName: String?,
    @Json(name = "hex_value")
    var hexValue: String?
)