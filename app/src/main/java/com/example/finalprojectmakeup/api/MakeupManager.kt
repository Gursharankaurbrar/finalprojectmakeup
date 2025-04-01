package com.example.finalprojectmakeup.api

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.finalprojectmakeup.api.db.AppDatabase
import com.example.finalprojectmakeup.api.model.MakeupDataItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response


class MakeupManager(database: AppDatabase) {
    private var _makeupResponse = mutableStateOf<List<MakeupDataItem>>(emptyList())

    val makeupResponse: MutableState<List<MakeupDataItem>>
        @Composable get() = remember { _makeupResponse }

    private val db = database

    init {
        getMakeup(db)
    }

    private fun getMakeup(database: AppDatabase) {
        val service = Api.retrofitService.getMakeupProducts()

        service.enqueue(object : retrofit2.Callback<List<MakeupDataItem>> {
            override fun onResponse(
                call: Call<List<MakeupDataItem>>,
                response: Response<List<MakeupDataItem>>
            ) {
                if (response.isSuccessful) {
                    val makeupData = response.body()
                    Log.i("API Response", "Data received: ${makeupData?.size} products")
                    _makeupResponse.value = makeupData ?: emptyList()

                    GlobalScope.launch {
                        saveDataToDatabase(database = database, makeups = _makeupResponse.value)}
                } else {
                    Log.e("API Error", "Response error")


                }
            }

            override fun onFailure(call: Call<List<MakeupDataItem>>, t: Throwable) {
                Log.e("API Failure", "Failed: ${t.message}")
            }
        })
    }

    private suspend fun saveDataToDatabase(database: AppDatabase, makeups: List<MakeupDataItem>){
        database.makeupDao().insertAll(makeups)
    }

    suspend fun refreshMakeups(){
        var makeups = db.makeupDao().getAllMakeup()
        _makeupResponse.value = makeups
    }
}