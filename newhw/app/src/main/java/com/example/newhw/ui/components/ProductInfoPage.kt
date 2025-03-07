package com.example.newhw.ui.components
import androidx.compose.animation.animateContentSize
import androidx.compose.runtime.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.newhw.model.ProductInfoClass


// ui/components/ProductInfoPage.kt
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
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
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
