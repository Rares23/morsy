package com.crxapplications.morsy.core.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.crxapplications.morsy.flows.morse.presentation.page.ConverterPage
import com.crxapplications.morsy.flows.morse.presentation.page.PromptFormPage

fun NavGraphBuilder.rootNavigationGraph(navController: NavController) {
    composable(route = Route.PromptForm.route) {
        PromptFormPage()
    }

    composable(route = Route.Converter.route) {
        ConverterPage()
    }
}