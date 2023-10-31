package com.crxapplications.morsy.core.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.crxapplications.morsy.flows.morse.presentation.page.ConverterPage
import com.crxapplications.morsy.flows.morse.presentation.page.PromptFormPage

fun NavGraphBuilder.rootNavigationGraph(navController: NavHostController) {
    composable(route = Route.PromptForm.route) {
        PromptFormPage()
    }

    composable(route = Route.Converter.route) {
        ConverterPage()
    }
}