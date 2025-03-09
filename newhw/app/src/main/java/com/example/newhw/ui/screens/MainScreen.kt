package com.example.newhw.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import android.content.Context
import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.*
import coil.compose.AsyncImage
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip
import com.example.newhw.R
import com.example.newhw.model.ProductInfoClass
import com.example.newhw.ui.components.ProductInfoPage


// ui/screens/MainScreen.kt
@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    var savedName by remember { mutableStateOf<String?>(null) }
    var savedImageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(Unit) {
        val sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        savedName = sharedPreferences.getString("name", null)
        savedImageUri = sharedPreferences.getString("imageUri", null)?.let { Uri.parse(it) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD2A5AB))
    ) {
        // Top Header (User Name & Image)
        if (savedName != null && savedImageUri != null) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .background(Color(0xFFD2A5AB))
            ) {
                savedName?.let {
                    Text(
                        text = "Hi $it !",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                savedImageUri?.let { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = "User's Profile Image",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(CircleShape)
                    )
                }
            }
        }

        // Product List
        val products = listOf(
            ProductInfoClass("Tarte Shape-Tape Concealer", "Description here", R.drawable.shape_tape),
            ProductInfoClass("Nyx Fat Oil", "Description here", R.drawable.nyx_fat_oil),
            ProductInfoClass("Huda Ube Setting Powder", "Description here", R.drawable.ube_setting_powder),
            ProductInfoClass("Maybelline Sky High Mascara", "Description here", R.drawable.sky_high_mascara),
            ProductInfoClass("Benefit Hoola Bronzer", "Description here", R.drawable.benefit_hoola),
            ProductInfoClass("Tirtir Foundation", "Description here", R.drawable.tirtir_foundation)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 120.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            ) {
                item {
                    Surface(
                        color = Color(0xBABD6471),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "What does your makeup say about you?",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = MaterialTheme.colorScheme.onPrimary
                                ),
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
                items(products) { product -> ProductInfoPage(product) }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("order") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC2185B)),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Click here to order your makeup bundle!")
            }

            Button(
                onClick = {
                    val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE).edit()
                    sharedPreferences.clear()
                    sharedPreferences.apply()

                    savedName = null
                    savedImageUri = null
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Reset User Info")
            }
        }
    }
}

