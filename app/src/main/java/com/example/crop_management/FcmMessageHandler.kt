package com.example.crop_management

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FcmMessageHandler(message: String) {
    if (message.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { /* Dismiss logic */ },
            title = { Text(text = "New Message") },
            text = { Text(text = message) },
            confirmButton = {
                Button(onClick = { /* Confirm button logic */ }) {
                    Text(text = "OK")
                }
            }
        )
    }
}