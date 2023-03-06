package com.akra.intranet.presentation

sealed class Screen(val route: String) {
    object LoginScreen: Screen("login_screen")
    object HomeScreen: Screen("home_screen")
    object AddEditScreen: Screen("addedit_screen")
}