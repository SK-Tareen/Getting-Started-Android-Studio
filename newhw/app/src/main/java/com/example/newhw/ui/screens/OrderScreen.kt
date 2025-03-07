package com.example.newhw.ui.screens

import android.content.Context
import android.net.Uri
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.Manifest
import android.app.NotificationManager
import android.os.Build
import android.provider.Settings
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.newhw.data.SharedPreferencesHelper
import com.example.newhw.utils.NotificationHandler
import com.example.newhw.utils.SensorHandler
import com.example.newhw.ui.components.ImagePicker
import com.example.newhw.ui.components.OrderForm

@Composable
fun OrderScreen(navController: NavController) {
    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showErrorMessage by remember { mutableStateOf(false) }
    var showRotationMessage by remember { mutableStateOf(false) }
    var notificationTriggered by remember { mutableStateOf(false) }

    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri = it }
        }

    LaunchedEffect(Unit) {
        SharedPreferencesHelper.loadUserData(context) { savedName, savedCity, savedImageUri ->
            name = savedName ?: ""
            city = savedCity ?: ""
            imageUri = savedImageUri
        }
    }

    SensorHandler.registerGyroscopeListener(
        sensorManager,
        gyroscope,
        onRotation = {
            showRotationMessage = true
            NotificationHandler.showNotification(context)
            notificationTriggered = true
        }
    )

    ImagePicker(imageUri) { galleryLauncher.launch("image/*") }

    OrderForm(name, city, onNameChange = { name = it }, onCityChange = { city = it }) {
        if (name.isNotEmpty() && city.isNotEmpty()) {
            SharedPreferencesHelper.saveUserData(context, name, city, imageUri)
            navController.navigate("main")
        } else {
            showErrorMessage = true
        }
    }

    if (showErrorMessage) {
        // Show error message
    }
}
