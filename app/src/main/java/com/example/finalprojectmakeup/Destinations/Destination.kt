package com.example.finalprojectmakeup.Destinations

sealed class Destination(val route: String) {
    object Makeup: Destination("Makeup")
    object Search: Destination("Search")
    object Watch : Destination("Watch")
}