package com.example.saniahw1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.border
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.compose.ui.text.font.FontWeight
import com.example.saniahw1.ui.theme.SaniaHW1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SaniaHW1Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") { MainScreen(navController) }
                    composable("order") { OrderScreen(navController) }
                }
            }
        }
    }
}

data class ProductInfoClass(val productName: String, val productInfo: String, val imageResource: Int)

@Composable
fun MainScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD2A5AB))
    ) {
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
                        "live without **Maybelline Sky High**! With just a few swipes, " +
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
                        "can’t get enough of **Tirtir Foundation**! With its lightweight, buildable " +
                        "coverage, they effortlessly achieve a flawless, natural look. This beauty lover " +
                        "appreciates the breathable formula that feels like a second skin, perfect for " +
                        "all-day wear. Whether it's a work meeting or a night out, they know Tirtir " +
                        "provides that radiant finish that keeps them glowing and confident!",
                R.drawable.tirtir_foundation
            )
        )

        Column(
            modifier = Modifier.fillMaxSize()
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
                Text(text = "Click here to order your makeup bundle")
            }
        }
    }
}


@Composable
fun OrderScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC2185B)),
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)

            ) {
                Text(text = "Back")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            )
            {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Create your profile to place an order!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Name", style = MaterialTheme.typography.bodyLarge)
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
            Text(text = "Name", style = MaterialTheme.typography.bodyLarge)
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
        }
    }
}


@Composable
fun Conversation(messages: List<ProductInfoClass>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
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
        items(messages) { message ->
            ProductInfoPage(message)
        }
    }
}


@Composable
fun ProductInfoPage(info: ProductInfoClass) {
    var isExpanded by remember { mutableStateOf(false) }
    val surfaceColor by animateColorAsState(
        if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
    )

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
