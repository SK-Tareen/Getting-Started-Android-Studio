package com.example.newhw.ui.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun SplashScreen(navController: NavController) {
    // Display a progress indicator during the splash screen
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Add a delay before navigating to the main screen
        Handler(Looper.getMainLooper()).postDelayed({
            navController.navigate("main") {
                // Prevent navigating back to the splash screen by popping it
                popUpTo("splashscreen") { inclusive = true }
            }
        }, 500)
    }
}
