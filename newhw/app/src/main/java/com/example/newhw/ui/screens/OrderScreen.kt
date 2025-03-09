package com.example.newhw.ui.screens

import android.content.Context
import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.compose.runtime.remember
import androidx.core.content.ContextCompat

import coil.compose.AsyncImage

import com.example.newhw.ui.components.ImagePicker
import com.example.newhw.ui.components.OrderForm
import com.example.newhw.utils.NotificationHandler
import com.example.newhw.utils.SensorHandler
import com.example.newhw.utils.FileUtils
import android.media.MediaPlayer
import com.example.newhw.audio.AudioRecorder

import com.example.newhw.data.SharedPreferencesHelper


@Composable
fun OrderScreen(navController: NavController) {
    val context = LocalContext.current

    // State for form data
    var name by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showErrorMessage by remember { mutableStateOf(false) }

    // Audio variables
    val recorder = remember { AudioRecorder(context) }
    var audioFilePath by remember { mutableStateOf<String?>(null) }
    var isRecording by remember { mutableStateOf(false) }

    var mediaPlayer: MediaPlayer? by remember { mutableStateOf(null) }
    var isPlaying by remember { mutableStateOf(false) }

    // Permission request launcher
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Toast.makeText(context, "Permission granted!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission denied!", Toast.LENGTH_SHORT).show()
        }
    }

    // Image picker launcher
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                // Save image to internal storage using FileUtils
                val savedUri = FileUtils.saveImageToInternalStorage(context, it)
                savedUri?.let {
                    imageUri = it
                }
            }
        }

    // Load saved data using SharedPreferencesHelper
    LaunchedEffect(Unit) {
        SharedPreferencesHelper.loadUserData(context) { loadedName, loadedCity, loadedImageUri ->
            name = loadedName ?: ""
            city = loadedCity ?: ""
            imageUri = loadedImageUri
        }
    }

    // Gyroscope sensor listener setup
    var showRotationMessage by remember { mutableStateOf(false) }

    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

    // Register gyroscope listener to trigger notification on rotation
    LaunchedEffect(Unit) {
        SensorHandler.registerGyroscopeListener(sensorManager, gyroscope) {
            showRotationMessage = true
            NotificationHandler.showNotification(context)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD2A5AB))
    ) {
        // Back Button
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC2185B)),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp, top = 10.dp)
        ) {
            Text(text = "< Back")
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Main content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.Center)
        ) {
            // Title
            Text(
                text = "Create your profile to place an order!",
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Order Form Component
            OrderForm(
                name = name,
                city = city,
                onNameChange = { name = it },
                onCityChange = { city = it },
                onSave = {
                    if (name.isNotEmpty() && city.isNotEmpty()) {
                        // Save user data using SharedPreferencesHelper
                        SharedPreferencesHelper.saveUserData(context, name, city, imageUri)

                        // Save imageUri and other data for UI update
                        showErrorMessage = false

                        // Pass the data as navigation arguments
                        navController.navigate("main?name=$name&imageUri=${imageUri?.toString()}")
                    } else {
                        showErrorMessage = true
                        Toast.makeText(context, "Fill in all fields", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Image Picker Component
            ImagePicker(imageUri = imageUri, onPickImage = { galleryLauncher.launch("image/*") })

            // Error message for missing fields
            if (showErrorMessage) {
                Text(
                    text = "Fill in all fields",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Reset Account Information Button
            Button(
                onClick = {
                    // Reset user data in SharedPreferences
                    val sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                    sharedPreferences.edit().clear().apply()
                    name = ""
                    city = ""
                    imageUri = null
                    showErrorMessage = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC2185B)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Reset Account Information")
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Recorder button

            // Audio Recording Button
            Button(
                onClick = {
                    if (!isRecording) {
                        audioFilePath = recorder.startRecording()
                        isRecording = true
                    } else {
                        recorder.stopRecording()
                        isRecording = false
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = if (isRecording) Color.Red else Color.Green),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(if (isRecording) "Stop Recording" else "Start Recording")
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Play/Stop Recording Button
            Button(
                onClick = {
                    if (!isPlaying && audioFilePath != null) {
                        mediaPlayer?.release()
                        mediaPlayer = MediaPlayer().apply {
                            try {
                                setDataSource(audioFilePath)
                                prepare()
                                start()
                                isPlaying = true
                                setOnCompletionListener {
                                    isPlaying = false
                                    release()
                                    mediaPlayer = null
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    } else {
                        mediaPlayer?.let {
                            if (it.isPlaying) {
                                it.stop()
                                it.release()
                                mediaPlayer = null
                                isPlaying = false
                            }
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = if (isPlaying) Color.Gray else Color.Blue),
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(if (isPlaying) "Stop Playing" else "Play Recording")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Current Information Display
            Text(
                text = "Current Information",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp)
            )

            if (name.isEmpty() && city.isEmpty()) {
                Text(text = "No information", style = MaterialTheme.typography.bodyLarge)
            } else {
                name.let {
                    Text(
                        text = "Name: $it",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                city.let {
                    Text(
                        text = "City: $it",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                imageUri?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "User's Image",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                }
            }

            // Show rotation message when gyroscope event is detected
            if (showRotationMessage) {
                Text(
                    text = "Device rotated! Notification triggered.",
                    color = Color.Green,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
