package com.example.finalprojectmakeup.mvvm

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.finalprojectmakeup.api.Api
import com.example.finalprojectmakeup.api.db.AppDatabase
import com.example.finalprojectmakeup.api.db.MakeupDao
import com.example.finalprojectmakeup.api.model.MakeupDataItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Purpose - MakeupViewModel - handles UI-related data for makeup products, including favorite states, database operations, and data updates
 *
 * @param db: AppDatabase - Room database instance for accessing makeup data
 */
class MakeupViewModel(private val db: AppDatabase) : ViewModel(){

    // variable to keep track of the Makeup Icon state
    private val _makeupIconState = MutableStateFlow<Map<Int,Boolean>>(emptyMap())

    /**
     * Purpose - Exposes the makeup icon state as a StateFlow for UI observation
     *
     * @return StateFlow<Map<Int, Boolean>> - map of makeup IDs to their favorite states
     */
    val makeupIconState = _makeupIconState.asStateFlow()

    val favoriteMakeups: Flow<List<MakeupDataItem>> = db.makeupDao().getFavoriteMakeups()

    /**
     * Purpose - Provides access to the database instance
     *
     * @return AppDatabase - the Room database instance
     */
    fun getDatabase(): AppDatabase = db

    /**
     * Purpose - a function to update the  favorite state ie. true or false
     * @params makeupID: Int - represent the makeupID
     * @params database: AppDatabase - represent the database
     * @return unit
     */
    fun updateMakeupIcon(makeupID: Int, database: AppDatabase) {

        viewModelScope.launch(Dispatchers.IO) {
            val makeup = database.makeupDao().getMakeupById(makeupID)

            if ( makeup != null ){
                makeup.isFavorite = !makeup.isFavorite

                launch(Dispatchers.IO){
                    database.makeupDao().updateMakeupState(makeup)

                    _makeupIconState.value = _makeupIconState.value.toMutableMap().apply{
                        this[makeupID] = makeup.isFavorite
                    }
                }

            }else {
                Log.e("MovieViewModel", "Makeup with ID $makeupID not found in database")
            }


        }

    }

}