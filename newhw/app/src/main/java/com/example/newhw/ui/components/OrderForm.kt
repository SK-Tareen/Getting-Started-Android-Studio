package com.example.newhw.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.ui.unit.dp


@Composable
fun OrderForm(
    name: String,
    city: String,
    onNameChange: (String) -> Unit,
    onCityChange: (String) -> Unit,
    onSave: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Enter your full name")
        OutlinedTextField(value = name, onValueChange = onNameChange)

        Spacer(modifier = Modifier.height(8.dp))

        Text("Enter your city")
        OutlinedTextField(value = city, onValueChange = onCityChange)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onSave) {
            Text("Save Account Information")
        }
    }
}
