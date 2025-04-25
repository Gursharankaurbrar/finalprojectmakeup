package com.example.finalprojectmakeup.api

import com.example.finalprojectmakeup.api.model.MakeupData
import com.example.finalprojectmakeup.api.model.MakeupDataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MakeupService {
    // endpoints get defined here
    @GET("api/v1/products.json")
    fun getMakeupProducts(): Call<List<MakeupDataItem>>

}