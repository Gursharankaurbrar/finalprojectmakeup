package com.example.finalprojectmakeup.mvvm


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectmakeup.api.db.AppDatabase
import com.example.finalprojectmakeup.api.model.MakeupDataItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(private val db: AppDatabase) : ViewModel() {
    private val _searchResults = MutableStateFlow<List<MakeupDataItem>>(emptyList())
    val searchResults: StateFlow<List<MakeupDataItem>> = _searchResults.asStateFlow()

    fun searchMakeups(query: String) {
        viewModelScope.launch {
            if (query.isNotBlank()) {
                val results = db.makeupDao().searchMakeups("%$query%")
                _searchResults.value = results
            } else {
                _searchResults.value = emptyList()
            }
        }
    }
}