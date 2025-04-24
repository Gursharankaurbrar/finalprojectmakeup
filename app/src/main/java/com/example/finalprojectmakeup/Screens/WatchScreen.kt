package com.example.finalprojectmakeup.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.finalprojectmakeup.mvvm.MakeupViewModel
import com.example.finalprojectmakeup.api.db.AppDatabase

@Composable
fun WatchScreen(
    modifier: Modifier = Modifier,
    viewModel: MakeupViewModel,
    navController: NavController
) {  
    val favoriteMakeups by viewModel.favoriteMakeups.collectAsState(initial = emptyList())

    if (favoriteMakeups.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8BBD0)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "No favorites yet!",
                color = Color(0xFFE91E63),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    } else {
        LazyColumn(

        ) {
            items(favoriteMakeups) { makeup ->
              
                MakeupCard(
                    product = makeup,
                    navController = navController,
                    db = viewModel.getDatabase(),
                    viewModel = viewModel,
                    makeupManager = null
                )
            }
        }
    }
}