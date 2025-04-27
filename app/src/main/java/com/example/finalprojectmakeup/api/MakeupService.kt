package com.example.finalprojectmakeup.api

import com.example.finalprojectmakeup.api.model.MakeupData
import com.example.finalprojectmakeup.api.model.MakeupDataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Purpose - MakeupService - defines API endpoints to fetch makeup product data
 */
interface MakeupService {
    /**
     * Purpose - Retrieves a list of makeup products from the API
     *
     * @return Call<List<MakeupDataItem>> - Retrofit Call object containing a list of makeup products
     */
    @GET("api/v1/products.json")
    fun getMakeupProducts(): Call<List<MakeupDataItem>>

}