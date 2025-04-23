package com.example.finalprojectmakeup.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Singleton object to initialize Retrofit for accessing the Makeup API.
 * Uses Moshi as the JSON converter.
 */
object Api {

    // Base URL for the Makeup API
    private val BASE_URL = "https://makeup-api.herokuapp.com/"

    // Moshi object for JSON parsing with Kotlin adapter
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    // Retrofit instance with Moshi converter and base URL
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    // Lazy-initialized Retrofit service interface
    val retrofitService: MakeupService by lazy {
        retrofit.create(MakeupService::class.java)
    }
}