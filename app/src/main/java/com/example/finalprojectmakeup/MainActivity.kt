package com.example.finalprojectmakeup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectmakeup.Destinations.Destination
import com.example.finalprojectmakeup.Screens.MakeupScreen
import com.example.finalprojectmakeup.Screens.SearchScreen
import com.example.finalprojectmakeup.Screens.WatchScreen
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

                    App( navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavHostController, modifier: Modifier){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Makeup Final Project 2025")}
            )
        },
        bottomBar = {
            BottomNav(navController = navController)
        }
    ){ paddingValues ->
        paddingValues.calculateBottomPadding()
        Spacer(modifier = Modifier.padding(10.dp))
        NavHost(
            navController = navController as NavHostController,
            startDestination = Destination.Makeup.route
        ){
            composable(Destination.Makeup.route){
                MakeupScreen()
            }
            composable(Destination.Search.route){
                SearchScreen()
            }
            composable(Destination.Watch.route){
                WatchScreen()
            }
        }
    }
}

