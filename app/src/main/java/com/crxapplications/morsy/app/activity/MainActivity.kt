package com.crxapplications.morsy.app.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.crxapplications.morsy.core.navigation.Route
import com.crxapplications.morsy.ui.theme.MorsyTheme
import com.crxapplications.morsy.core.navigation.rootNavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MorsyTheme {
                val navController: NavHostController = rememberNavController()

                NavHost(
                    navController = navController,
                    route = Route.Root.route,
                    startDestination = Route.PromptForm.route,
                ) {
                    rootNavigationGraph(navController = navController)
                }
            }
        }
    }
}