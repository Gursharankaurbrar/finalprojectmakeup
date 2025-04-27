package com.example.finalprojectmakeup

import android.annotation.SuppressLint
import android.graphics.Movie
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.finalprojectmakeup.Destinations.Destination
import com.example.finalprojectmakeup.Screens.AuthenticationScreen
import com.example.finalprojectmakeup.Screens.LoginScreen
import com.example.finalprojectmakeup.Screens.MakeupDetailScreen
import com.example.finalprojectmakeup.Screens.MakeupScreen
import com.example.finalprojectmakeup.Screens.SearchScreen
import com.example.finalprojectmakeup.Screens.WatchScreen
import com.example.finalprojectmakeup.api.MakeupManager
import com.example.finalprojectmakeup.api.db.AppDatabase
import com.example.finalprojectmakeup.api.model.MakeupDataItem
import com.example.finalprojectmakeup.api.model.ProductColor
import com.example.finalprojectmakeup.mvvm.MakeupViewModel
import com.example.finalprojectmakeup.mvvm.SearchViewModel
import com.example.finalprojectmakeup.ui.theme.FinalProjectMakeupTheme
import com.example.movieproject.Navigation.BottomNav
import com.google.firebase.Firebase
import com.google.firebase.initialize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * MainActivity serves as the entry point of the app. It handles Firebase initialization,
 * authentication state management, and sets up the navigation graph.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(this)
        enableEdgeToEdge()
        setContent {
            FinalProjectMakeupTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    val authViewModel: AuthViewModel = viewModel()

                    val db = AppDatabase.getInstance(applicationContext)


                    val makeupManager = MakeupManager(db)

                    val viewModel: MakeupViewModel = ViewModelProvider(
                        this,
                        object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return MakeupViewModel(db) as T
                            }
                        }
                    )[MakeupViewModel::class.java]

                    val searchViewModel: SearchViewModel = ViewModelProvider(
                        this,
                        object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                return SearchViewModel(db) as T
                            }
                        }
                    )[SearchViewModel::class.java]

                    App( navController = navController, modifier = Modifier.padding(innerPadding), makeupManager, db, authViewModel, viewModel, searchViewModel)
                }
            }
        }
    }
}

/**
 * The App composable is the root composable that sets up the entire app's UI structure.
 * @param navController: NavHostController - A controller for navigating between screens.
 * @param modifier: Modifier - A modifier to apply styles and adjustments to the composable.
 * @param makeupManager: MakeupManager - A manager for handling makeup-related data.
 * @param db: AppDatabase - The local database instance for interacting with stored data.
 * @param authViewModel: AuthViewModel - The view model for handling user authentication.
 * @param viewModel: MakeupViewModel - The view model for handling makeup-related data and logic.
 * @param searchViewModel: SearchViewModel - The view model for handling search functionality.
 * @return Unit
 */
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    navController: NavHostController,
    modifier: Modifier,
    makeupManager: MakeupManager,
    db: AppDatabase,
    authViewModel: AuthViewModel,
    viewModel: MakeupViewModel,
    searchViewModel: SearchViewModel
) {
    var makeup by remember {
        mutableStateOf<MakeupDataItem?>(null)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute !in listOf(
        Destination.Login.route,
        Destination.Authentication.route
    )

    val startDestination = if (authViewModel.isUserLoggedIn) {
        Destination.Makeup.route
    } else {
        Destination.Login.route
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Makeup Final Project 2025",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    if (authViewModel.isUserLoggedIn) {
                        Button(
                            onClick = {
                                authViewModel.signOut()
                                navController.navigate(Destination.Login.route)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFF9C144D)
                            ),
                            modifier = Modifier
                                .height(36.dp)
                                .padding(end = 8.dp)
                        ) {
                            Text("Logout", fontWeight = FontWeight.Bold)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF8B1A3C)
                )
            )
        },
        bottomBar = {
            if (showBottomBar) {
                BottomNav(navController = navController)
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFF8B1A3C))
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable(Destination.Login.route) {
                    LoginScreen(navController, authViewModel)
                }
                composable(Destination.Authentication.route) {
                    AuthenticationScreen(navController, authViewModel)
                }
                composable(Destination.Makeup.route) {
                    MakeupScreen(modifier, makeupManager, navController, db, authViewModel,  viewModel = viewModel)
                }
                composable(Destination.Search.route) {
                    SearchScreen( navController = navController,
                        db = db,
                        makeupViewModel = viewModel,
                        searchViewModel = searchViewModel)
                }
                composable(Destination.Watch.route) {
                    WatchScreen(viewModel = viewModel,
                        navController = navController)
                }
                composable(Destination.MakeupDetail.route) { navBackStackEntry ->
                    val makeupId: String? = navBackStackEntry.arguments?.getString("makeupID")

                    LaunchedEffect(makeupId) {
                        if (makeupId != null) {
                            withContext(Dispatchers.IO) {
                                makeup = db.makeupDao().getMakeupById(makeupId.toInt())
                            }
                        }
                    }

                    makeup?.let { item ->
                        MakeupDetailScreen(
                            makeupDataItem = item,
                            db = db,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}

