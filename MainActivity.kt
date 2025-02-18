package com.example.newhw

import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import android.Manifest
import android.content.Context
import java.io.FileOutputStream
import java.io.IOException
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.ui.zIndex
import android.content.SharedPreferences
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import coil.compose.AsyncImage
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File
import java.util.concurrent.Executors
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.newhw.ui.theme.NewhwTheme
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.PermissionState

@Composable
fun ProductInfoPage(info: ProductInfoClass) {
    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { isExpanded = !isExpanded }
            .animateContentSize()
            .border(1.dp, color = Color(0xBABD6471), RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = painterResource(info.imageResource),
            contentDescription = "Makeup product",
            modifier = Modifier
                .fillMaxWidth()
                .size(120.dp)
                .clip(RoundedCornerShape(8.dp))

        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = info.productName,
            color = Color(0xBAC54457),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        if (isExpanded) {
            Text(
                text = info.productInfo,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewhwTheme {
                val navController = rememberNavController()

                // Use the existing getUserData function, move it to a Composable scope
                val (savedName, savedImageUri) = remember { getUserData(this@MainActivity) }

                // Set up navigation with saved user data
                NavHost(navController = navController, startDestination = "main") {
                    composable("main?name={name}&imageUri={imageUri}") { backStackEntry ->
                        val name = backStackEntry.arguments?.getString("name") ?: savedName
                        val imageUri = backStackEntry.arguments?.getString("imageUri") ?: savedImageUri
                        MainScreen(navController = navController)
                    }

                    composable("order") { OrderScreen(navController) }
                }
            }
        }
    }
}

fun getUserData(context: Context): Pair<String?, String?> {
    val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    val name = sharedPreferences.getString("user_name", null)
    val imagePath = sharedPreferences.getString("user_image_uri", null)

    // Ensure the file still exists before returning it
    val imageFile = imagePath?.let { File(it) }
    return if (imageFile != null && imageFile.exists()) {
        Pair(name, Uri.fromFile(imageFile).toString())
    } else {
        Pair(name, null)
    }
}



fun saveUserData(context: Context, name: String, imageUri: Uri) {
    val sharedPreferences = context.getSharedPreferences("user_data", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("user_name", name)
    editor.putString("user_image_uri", imageUri.path) // Save file path
    editor.apply()
}


data class ProductInfoClass(val productName: String, val productInfo: String, val imageResource: Int)



@Composable
fun MainScreen(navController: NavController) {
    val context = LocalContext.current
    var savedName by remember { mutableStateOf<String?>(null) }
    var savedImageUri by remember { mutableStateOf<String?>(null) }

    // Retrieve the name and imageUri from navigation arguments
    val backStackEntry = remember { navController.currentBackStackEntry }
    val nameFromArgs = backStackEntry?.arguments?.getString("name")
    val imageUriFromArgs = backStackEntry?.arguments?.getString("imageUri")

    LaunchedEffect(Unit) {
        if (nameFromArgs != null && imageUriFromArgs != null) {
            savedName = nameFromArgs
            savedImageUri = imageUriFromArgs
        } else {
            val (nameSP, imageUriSP) = getUserData(context)
            savedName = nameSP
            savedImageUri = imageUriSP
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD2A5AB))
    ) {
        // Top Header (Name and Image)
        if (savedName != null && savedImageUri != null) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopStart)
                    .background(Color(0xFFD2A5AB))
                    .zIndex(1f) // Ensure it stays on top of the scrollable content
            ) {
                savedName?.let {
                    Text(
                        text = "Hi $it !",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                AsyncImage(
                    model = savedImageUri?.let { Uri.parse(it) }, // Convert string to Uri
                    contentDescription = "User's Image",
                    modifier = Modifier.size(50.dp).clip(CircleShape)
                )
            }
        }

        val products = listOf(
            ProductInfoClass(
                "Tarte Shape-Tape Concealer",
                "Meet the ultimate multitasker " +
                        "who loves to keep their beauty game on point—someone who uses the " +
                        "Shape Tape Concealer! With a knack for transforming under-eye bags into a " +
                        "flawless canvas, they're always ready to tackle a busy day or an impromptu " +
                        "night out. Always chic and effortlessly fresh, " +
                        "they believe that confidence begins with a flawless base!",
                R.drawable.shape_tape
            ),
            ProductInfoClass(
                "Nyx Fat Oil",
                "Meet the savvy beauty lover who " +
                        "swears by NYX Fat Oil! This fabulous multitasker knows how to " +
                        "elevate their lip game with just a swipe. " +
                        "With a high-shine finish and nourishing formula, " +
                        "they effortlessly achieve that juicy, glossy look while keeping lips hydrated. " +
                        "Perfect for on-the-go glam, " +
                        "they're always ready to shine bright, no matter the occasion!",
                R.drawable.nyx_fat_oil
            ),
            ProductInfoClass(
                "Huda Ube Setting Power",
                "Meet the trendsetter who loves " +
                        "Huda Beauty Ube Setting Powder! With a sprinkle of this magical powder, " +
                        "they banish shine and embrace a radiant glow. Always selfie-ready, " +
                        "they flaunt an airbrushed finish, leaving everyone curious about their secret. " +
                        "With a playful wink, they inspire friends to unleash their inner glam!",
                R.drawable.ube_setting_powder
            ),
            ProductInfoClass(
                "Maybelline Sky High Mascara",
                "Meet the mascara enthusiast who can’t " +
                        "live without Maybelline Sky High! With just a few swipes, " +
                        "they achieve sky-high lashes that flutter beautifully. " +
                        "This beauty guru embraces voluminous length and definition, " +
                        "making every blink captivating. Always ready for a night out or a casual brunch, " +
                        "they know their lashes are the ultimate showstopper!",
                R.drawable.sky_high_mascara
            ),
            ProductInfoClass(
                "Benefit Hoola Bronzer",
                "Meet the bronzer lover who swears by " +
                        "Benefit Hoola! With a quick sweep of this matte marvel, they effortlessly " +
                        "achieve that sun-kissed glow, no beach required. Always game for a little " +
                        "contouring magic, they know Hoola is the key to sculpted cheeks and a warm, " +
                        "radiant complexion. Whether it's a casual day out or a night on the town, " +
                        "this beauty aficionado knows they’ll look flawless and fresh!",
                R.drawable.benefit_hoola
            ),
            ProductInfoClass(
                "Tirtir Foundation",
                "Meet the makeup maven who " +
                        "can’t get enough of Tirtir Foundation! With its lightweight, buildable " +
                        "coverage, they effortlessly achieve a flawless, natural look. This beauty lover " +
                        "appreciates the breathable formula that feels like a second skin, perfect for " +
                        "all-day wear. Whether it's a work meeting or a night out, they know Tirtir " +
                        "provides that radiant finish that keeps them glowing and confident!",
                R.drawable.tirtir_foundation
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
        ) {
            // LazyColumn for the product list
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
                items(products) { product ->
                    ProductInfoPage(product)
                }
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
                    val file = File(context.filesDir, "saved_image.jpg")
                    file.delete() // Delete the stored image
                    val editor = context.getSharedPreferences("user_data", Context.MODE_PRIVATE).edit()
                    editor.remove("user_name")
                    editor.remove("user_image_uri")
                    editor.apply()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "Reset User Info")
            }
        }
    }
}

fun saveImageToInternalStorage(context: Context, uri: Uri): Uri? {
    try {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.filesDir, "saved_image.jpg")
        val outputStream = FileOutputStream(file)

        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()

        return Uri.fromFile(file) // Return the new internal storage URI
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}


@Composable
fun OrderScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var showErrorMessage by remember { mutableStateOf(false) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current // Get context
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                val savedUri = saveImageToInternalStorage(context, it)
                savedUri?.let {
                    imageUri = it
                }
            }
        }
    var savedName by remember { mutableStateOf<String?>(null) }
    var savedCity by remember { mutableStateOf<String?>(null) }
    var savedImageUri by remember { mutableStateOf<Uri?>(null) }

    // Load saved data from SharedPreferences
    LaunchedEffect(Unit) {
        val sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        savedName = sharedPreferences.getString("name", null)
        savedCity = sharedPreferences.getString("city", null)
        savedImageUri =
            savedName?.let { Uri.parse(sharedPreferences.getString("imageUri", "")) }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC2185B)),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 10.dp, top = 10.dp)
        ) {
            Text(text = "< Back")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopCenter)
                .padding(top = 48.dp)
        ) {
            Text(
                text = "Create your profile to place an order!",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Enter your full name", style = MaterialTheme.typography.bodyLarge)
            BasicTextField(
                value = name,
                onValueChange = { name = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFFF0F0F0))
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Enter your city", style = MaterialTheme.typography.bodyLarge)
            BasicTextField(
                value = city,
                onValueChange = { city = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFFF0F0F0))
                    .padding(16.dp)
            )


            Spacer(modifier = Modifier.height(16.dp))

            Button(

                onClick = { galleryLauncher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC2185B)),
            ) {
                Text(text = "Select from Gallery")
            }

            imageUri?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "Selected Image",
                    modifier = Modifier.size(200.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { imageUri = null },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                ) {
                    Text("Reset Photo")
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (name.isNotEmpty() && city.isNotEmpty()) {
                        val sharedPreferences =
                            context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                        sharedPreferences.edit().apply {
                            putString("name", name)
                            putString("city", city)
                            sharedPreferences.getString("imageUri", imageUri?.toString())?.let {
                                putString("imageUri", imageUri.toString())
                            }
                            apply()
                        }
                        savedName = name
                        savedCity = city
                        savedImageUri = imageUri
                        showErrorMessage = false

                        // Pass the data as navigation arguments
                        navController.navigate("main?name=$savedName&imageUri=${savedImageUri?.toString()}")
                    } else {
                        showErrorMessage = true
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC2185B)),
            ) {
                Text(text = "Save Account Information")
            }


            if (showErrorMessage) {
                Text(
                    text = "Fill in all fields",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val sharedPreferences =
                        context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
                    sharedPreferences.edit().clear().apply()
                    savedName = null
                    savedCity = null
                    imageUri = null
                    savedImageUri = null
                    showErrorMessage = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC2185B)),
            ) {
                Text(text = "Reset Account Information")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Current Information",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp)
            )

            if (savedName == null && savedCity == null) {
                Text(text = "No information", style = MaterialTheme.typography.bodyLarge)
            } else {
                savedName?.let {
                    Text(
                        text = "Name: $it",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                savedCity?.let {
                    Text(
                        text = "City: $it",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                AsyncImage(
                    model = savedImageUri,
                    contentDescription = "User's Image",
                    modifier = Modifier.size(50.dp).clip(CircleShape)
                )
            }
        }
    }
}