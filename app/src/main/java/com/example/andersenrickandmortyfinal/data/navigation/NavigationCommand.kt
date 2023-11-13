package com.example.andersenrickandmortyfinal.data.navigation

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    class ToDirections(val directions: NavDirections) : NavigationCommand()
    data class Back(val back: Boolean) : NavigationCommand()
    object Null : NavigationCommand()

}