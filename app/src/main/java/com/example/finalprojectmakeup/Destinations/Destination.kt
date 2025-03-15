package com.example.finalprojectmakeup.Destinations

sealed class Destination(val route: String) {
    object Makeup: Destination("makeup")
    object Search: Destination("search")
    object Watch : Destination("watch")
}