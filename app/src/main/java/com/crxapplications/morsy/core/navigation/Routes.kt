package com.crxapplications.morsy.core.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Route(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {
    object Root: Route(route = "root")
    object PromptForm : Route(route = "prompt-form")
    object Converter : Route(
        route = "converter/{text}",
        arguments = listOf(navArgument("text") { type = NavType.IntType })
    )
}