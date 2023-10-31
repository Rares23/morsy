package com.crxapplications.morsy.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Route(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {
    object Home : Route(route = "home")
    object Converter : Route(
        route = "converter/{id}",
        arguments = listOf(navArgument("id") { type = NavType.IntType })
    )
}