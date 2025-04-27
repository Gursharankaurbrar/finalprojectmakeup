package com.example.movieproject.Navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.finalprojectmakeup.Destinations.Destination
import com.example.finalprojectmakeup.R

/**
 * Purpose - BottomNav - displays a bottom navigation bar with icons to navigate
 * between Makeup, Search, and Favorites screens
 *
 * @param navController: NavController - controller to manage screen navigation
 * @return Unit
 */
@Composable
fun BottomNav(navController: NavController){
    NavigationBar(
        containerColor = Color(0xFF8B1A3C)
    ){
        var navBackStackEntry = navController.currentBackStackEntryAsState()
        var currentDestination = navBackStackEntry.value?.destination

        var ic_makeup = painterResource(id= R.drawable.ic_makeup)
        var ic_search = painterResource(id= R.drawable.ic_search)
        var ic_watch = painterResource(id = R.drawable.ic_watch)

        NavigationBarItem(
            selected = currentDestination?.route == Destination.Makeup.route,
            onClick = { navController.navigate(Destination.Makeup.route) {
                popUpTo(Destination.Makeup.route)
                launchSingleTop = true
            } },
            icon = { Icon(painter = ic_makeup, contentDescription = "Makeup", tint = Color.White) },
            label = { Text(text = Destination.Makeup.route, color = Color.White) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFFE57373)
            )
        ) // end makeup
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Search.route,
            onClick = { navController.navigate(Destination.Search.route) {
                popUpTo(Destination.Search.route)
                launchSingleTop = true
            } },
            icon = { Icon(painter = ic_search, contentDescription = "Search", tint = Color.White) },
            label = { Text(text = Destination.Search.route, color = Color.White) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFFE57373)
            )
        ) // end search
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Watch.route,
            onClick = { navController.navigate(Destination.Watch.route) {
                popUpTo(Destination.Watch.route)
                launchSingleTop = true
            } },
            icon = { Icon(painter = ic_watch, contentDescription = "Watch Later Screen icon", tint = Color.White) },
            label = { Text(text = Destination.Watch.route, color = Color.White) },
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color(0xFFE57373)
            )
        ) // end fav


    }
}

