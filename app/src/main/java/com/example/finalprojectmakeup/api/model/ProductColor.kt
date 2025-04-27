package com.example.finalprojectmakeup.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class representing a color option for a makeup product
 **/
@JsonClass(generateAdapter = true)
data class ProductColor(
    @Json(name = "colour_name")
    var colourName: String?,
    @Json(name = "hex_value")
    var hexValue: String?
)