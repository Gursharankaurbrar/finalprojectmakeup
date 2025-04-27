package com.example.finalprojectmakeup.api.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Purpose - MakeupData class - represents a list of makeup product data fetched from the API
 *
 * @constructor Creates an empty ArrayList of MakeupDataItem
 */
class MakeupData : ArrayList<MakeupDataItem>()