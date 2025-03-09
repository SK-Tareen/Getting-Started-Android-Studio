package com.example.newhw

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.newhw.navigation.NavGraph
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen() // Must be called before super.onCreate()
        super.onCreate(savedInstanceState)

        // No delay—directly setting content
        setContent {
            val navController = rememberNavController()
            NavGraph(navController)
        }
    }
}
