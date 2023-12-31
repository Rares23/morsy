package com.crxapplications.morsy.core.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.crxapplications.morsy.flows.morse.presentation.page.ConverterPage
import com.crxapplications.morsy.flows.morse.presentation.page.PromptFormPage
import com.crxapplications.morsy.flows.morse.presentation.viewmodel.ConverterEvent
import com.crxapplications.morsy.flows.morse.presentation.viewmodel.ConverterViewModel

fun NavGraphBuilder.rootNavigationGraph(navController: NavController) {
    composable(route = Route.PromptForm.route) {
        PromptFormPage(
            openConverterPage = { text ->
                navController.navigate(
                    route = Route.Converter.route.replace(
                        "{text}",
                        text
                    )
                )
            }
        )
    }

    composable(route = Route.Converter.route) { navBackStackEntry ->
        ConverterPage(text = navBackStackEntry.arguments?.getString("text") ?: "", navigateUp = {
            navController.navigateUp()
        })
    }
}