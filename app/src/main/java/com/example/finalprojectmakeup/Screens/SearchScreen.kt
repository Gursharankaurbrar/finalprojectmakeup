package com.example.finalprojectmakeup.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.finalprojectmakeup.api.MakeupManager
import com.example.finalprojectmakeup.api.db.AppDatabase
import com.example.finalprojectmakeup.mvvm.MakeupViewModel
import com.example.finalprojectmakeup.mvvm.SearchViewModel
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.material3.ButtonDefaults


/**
 * Purpose - Search Screen - to lookup makeups from API
 * @param navController: NavController - to handle navigation
 * @param db: AppDatabase - database instance
 * @param makeupViewModel: MakeupViewModel - for managing makeup-related state
 * @param searchViewModel: SearchViewModel - for handling search logic
 * @return Unit
 */
@Composable
fun SearchScreen(
    navController: NavController,
    db: AppDatabase,
    makeupViewModel: MakeupViewModel,
    searchViewModel: SearchViewModel
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val searchResults by searchViewModel.searchResults.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Search Row with TextField and Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Search TextField
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Search by brand or name") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            performSearch(searchQuery, searchViewModel, keyboardController, focusManager)
                        }
                    ),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color(0xFFE91E63),
                        focusedLabelColor = Color(0xFFE91E63)
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Go Button
                Button(
                    onClick = {
                        performSearch(searchQuery, searchViewModel, keyboardController, focusManager)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63),
                        contentColor = Color.White
                    ),
                    modifier = Modifier.height(56.dp) // Match TextField height
                ) {
                    Text("Go", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Search Results
            if (searchResults.isEmpty()) {
                if (searchQuery.isNotBlank()) {
                    Text(
                        "No results found for '$searchQuery'",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.White
                    )
                } else {
                    Text(
                        "Enter a search term to find makeup products",
                        color = Color.White,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            } else {
                LazyColumn {
                    items(searchResults) { makeup ->
                        MakeupCard(
                            product = makeup,
                            navController = navController,
                            db = db,
                            makeupManager = null,
                            viewModel = makeupViewModel
                        )
                    }
                }
            }
        }
    }
}

private fun performSearch(
    query: String,
    searchViewModel: SearchViewModel,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager
) {
    val searchTerm = if (query.isBlank()) "%%" else "%$query%"
    searchViewModel.searchMakeups(searchTerm)
    keyboardController?.hide()
    focusManager.clearFocus()
}