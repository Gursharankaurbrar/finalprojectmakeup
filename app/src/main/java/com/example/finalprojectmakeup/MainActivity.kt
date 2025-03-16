package com.example.finalprojectmakeup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectmakeup.Destinations.Destination
import com.example.finalprojectmakeup.Screens.AuthenticationScreen
import com.example.finalprojectmakeup.Screens.MakeupDetailScreen
import com.example.finalprojectmakeup.Screens.MakeupScreen
import com.example.finalprojectmakeup.Screens.SearchScreen
import com.example.finalprojectmakeup.Screens.WatchScreen
import com.example.finalprojectmakeup.api.MakeupManager
import com.example.finalprojectmakeup.api.model.MakeupDataItem
import com.example.finalprojectmakeup.api.model.ProductColor
import com.example.finalprojectmakeup.ui.theme.FinalProjectMakeupTheme
import com.example.movieproject.Navigation.BottomNav


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinalProjectMakeupTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val makeupManager = MakeupManager()

                    App( navController = navController, modifier = Modifier.padding(innerPadding), makeupManager)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavHostController, modifier: Modifier, makeupManager: MakeupManager){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    "Makeup Final Project 2025",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF8B1A3C)
                )
            )
        },
        bottomBar = {
            BottomNav(navController = navController)
        }
    ){ paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize().background(Color(0xFF8B1A3C))) {
            NavHost(
                navController = navController,
                startDestination = Destination.Authentication.route

            ){
                composable(Destination.Authentication.route) {
                    AuthenticationScreen(navController)
                }
                composable(Destination.Makeup.route){
                    MakeupScreen(modifier, makeupManager, navController)
                }
                composable(Destination.Search.route){
                    SearchScreen()
                }
                composable(Destination.Watch.route){
                    WatchScreen()
                }
                composable(Destination.MakeupDetail.route){
                    val makeupDataItem =
                    MakeupDataItem(
                        apiFeaturedImage = "https://example.com/images/demo-makeup.png",
                        brand = "Glamorous Glow",
                        category = "Lipstick",
                        createdAt = "2025-01-15T10:00:00Z",
                        currency = "USD",
                        description = "Makeup Details Screen",
                        id = 12345,
                        imageLink = "https://example.com/products/demo-lipstick.png",
                        name = "Velvet Red Lipstick",
                        price = "19.99",
                        priceSign = "$",
                        productApiUrl = "https://api.example.com/products/12345",
                        productColors = listOf(
                            ProductColor(
                                hexValue = "#B22222",
                                colourName = "Crimson Red"
                            ),
                            ProductColor(
                                hexValue = "#8B0000",
                                colourName = "Dark Red"
                            )
                        ),
                        productLink = "https://example.com/products/demo-lipstick",
                        productType = "lipstick",
                        rating = 4.5,
                        tagList = listOf("cruelty-free", "vegan", "long-lasting"),
                        updatedAt = "2025-03-10T15:00:00Z",
                        websiteLink = "https://example.com"
                    )

                    MakeupDetailScreen( makeupDataItem)
                }
            }
        }
    }
}

