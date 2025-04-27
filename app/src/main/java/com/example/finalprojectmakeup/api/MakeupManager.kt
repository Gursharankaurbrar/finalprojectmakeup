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

/**
 * Purpose - MakeupManager - manages fetching makeup data from the API, saving it to the database, and exposing it to the UI
 *
 * @param database: AppDatabase - Room database instance used for saving and retrieving makeup data
 */
class MakeupManager(database: AppDatabase) {
    private var _makeupResponse = mutableStateOf<List<MakeupDataItem>>(emptyList())

    /**
     * Purpose - Provides a stateful list of makeup products for UI to observe
     *
     * @return MutableState<List<MakeupDataItem>> - the current list of makeup items
     */
    val makeupResponse: MutableState<List<MakeupDataItem>>
        @Composable get() = remember { _makeupResponse }

    private val db = database

    init {
        getMakeup(db)
    }

    /**
     * Purpose - Fetches makeup products from the API and saves them into the Room database
     *
     * @param database: AppDatabase - database instance to save fetched data
     * @return Unit
     */
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

    /**
     * Purpose - Saves fetched makeup data into the Room database
     *
     * @param database: AppDatabase - database instance to insert makeup data into
     * @param makeups: List<MakeupDataItem> - list of makeup items to be inserted
     * @return Unit
     */
    private suspend fun saveDataToDatabase(database: AppDatabase, makeups: List<MakeupDataItem>){
        database.makeupDao().insertAll(makeups)
    }

    /**
     * Purpose - Refreshes the local makeup data from the Room database
     *
     * @return Unit
     */
    suspend fun refreshMakeups() {
        val makeups = db.makeupDao().getAllMakeup()
        Log.d("DEBUG", "Refreshed data: ${makeups.size} items")
        _makeupResponse.value = makeups
    }
}