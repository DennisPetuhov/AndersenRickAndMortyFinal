package com.example.andersenrickandmortyfinal.data.navigation

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    class ToDirections(val directions: NavDirections) : NavigationCommand()
    object Back : NavigationCommand()
    object Null : NavigationCommand()

}