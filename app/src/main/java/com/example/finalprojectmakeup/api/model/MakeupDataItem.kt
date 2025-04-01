package com.example.finalprojectmakeup.api.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@Entity(tableName = "makeups")
@JsonClass(generateAdapter = true)
data class MakeupDataItem(
    @Json(name = "api_featured_image")
    var apiFeaturedImage: String?,
    @Json(name = "brand")
    var brand: String?,
    @Json(name = "category")
    var category: String?,
    @Json(name = "created_at")
    var createdAt: String?,
    @Json(name = "currency")
    var currency: String?,
    @Json(name = "description")
    var description: String?,
    @Json(name = "id")
    @PrimaryKey(autoGenerate = false)
    var id: Int?,
    @Json(name = "image_link")
    var imageLink: String?,
    @Json(name = "name")
    var name: String?,
    @Json(name = "price")
    var price: String?,
    @Json(name = "price_sign")
    var priceSign: String?,
    @Json(name = "product_api_url")
    var productApiUrl: String?,
    //@Json(name = "product_colors")
    //var productColors: List<ProductColor?>?,
    @Json(name = "product_link")
    var productLink: String?,
    @Json(name = "product_type")
    var productType: String?,
    @Json(name = "rating")
    var rating: Double?,
    //@Json(name = "tag_list")
    //var tagList: List<String?>?,
    @Json(name = "updated_at")
    var updatedAt: String?,
    @Json(name = "website_link")
    var websiteLink: String?
)