package com.example.finalprojectmakeup.mvvm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectmakeup.api.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MakeupViewModel : ViewModel(){

    // variable to keep track of the Makeup Icon state
    private val _makeupIconState = MutableStateFlow<Map<Int,Boolean>>(emptyMap())
    val makeupIconState = _makeupIconState.asStateFlow()

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