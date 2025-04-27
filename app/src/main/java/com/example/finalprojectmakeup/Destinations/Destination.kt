package com.example.finalprojectmakeup.Destinations

/**
 * Purpose - Destination - defines all navigation routes used in the application
 *
 * @param route: String - string representing the navigation route
 */
sealed class Destination(val route: String) {
    /**
     * Purpose - Authentication screen destination
     */
    object Authentication :Destination("Authentication")
    /**
     * Purpose - Login screen destination
     */
    object Login :Destination("Login")
    /**
     * Purpose - Home screen destination
     */
    object Makeup: Destination("Makeup")
    /**
     * Purpose - Search screen destination
     */
    object Search: Destination("Search")
    /**
     * Purpose - Favorites screen destination
     */
    object Watch : Destination("Favorites")
    /**
     * Purpose - Makeup Details screen destination
     */
    object MakeupDetail : Destination("makeupDetail/{makeupID}")
}