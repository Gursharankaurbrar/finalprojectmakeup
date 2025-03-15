package com.example.movieproject.Navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController

import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.finalprojectmakeup.Destinations.Destination
import com.example.finalprojectmakeup.R


@Composable
fun BottomNav(navController: NavController){
    NavigationBar{
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
            icon = { Icon(painter = ic_makeup, contentDescription = "Makeup") },
            label = { Text(text = Destination.Makeup.route) }
        ) // end makeup
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Search.route,
            onClick = { navController.navigate(Destination.Search.route) {
                popUpTo(Destination.Search.route)
                launchSingleTop = true
            } },
            icon = { Icon(painter = ic_search, contentDescription = "Search") },
            label = { Text(text = Destination.Search.route) }
        ) // end search
        NavigationBarItem(
            selected = currentDestination?.route == Destination.Watch.route,
            onClick = { navController.navigate(Destination.Watch.route) {
                popUpTo(Destination.Watch.route)
                launchSingleTop = true
            } },
            icon = { Icon(painter = ic_watch, contentDescription = "Watch Later Screen icon") },
            label = { Text(text = Destination.Watch.route) }
        ) // end watch
    }
}

