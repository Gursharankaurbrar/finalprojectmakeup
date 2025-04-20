package com.example.finalprojectmakeup.Destinations

sealed class Destination(val route: String) {
    object Authentication :Destination("Authentication")
    object Login :Destination("Login")
    object Makeup: Destination("Makeup")
    object Search: Destination("Search")
    object Watch : Destination("Favorites")
    object MakeupDetail : Destination("makeupDetail/{makeupID}")
}