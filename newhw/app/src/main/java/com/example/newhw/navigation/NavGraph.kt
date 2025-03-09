package com.example.newhw.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newhw.ui.screens.MainScreen
import com.example.newhw.ui.screens.OrderScreen
import com.example.newhw.ui.screens.SplashScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "splashscreen") {
        composable("main") { MainScreen(navController) }
        composable("order") { OrderScreen(navController) }
        composable("splashscreen") { SplashScreen(navController) }

    }
}
