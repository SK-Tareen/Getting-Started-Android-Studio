package com.example.newhw.ui.components

import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp


@Composable
fun ImagePicker(imageUri: Uri?, onPickImage: () -> Unit) {
    Button(onClick = onPickImage) {
        Text("Select from Gallery")
    }

    imageUri?.let {
        AsyncImage(model = it, contentDescription = "Selected Image", modifier = Modifier.size(200.dp))
    }
}
