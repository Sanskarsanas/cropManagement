package com.example.crop_management

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import com.google.firebase.database.FirebaseDatabase


@Composable
fun Notifications(activity: ComponentActivity) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp)
    ) {
        ItemListScreenforNotification()
    }
}


@SuppressLint(
    "UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation",
    "StateFlowValueCalledInComposition"
)
@Composable
fun ItemListScreenforNotification() {

    val viewModel: ViewmodelforReminderNotification = viewModel()

    val items by viewModel.items.collectAsState()
    val context = LocalContext.current


    if (items.isEmpty()) {
        ShowProgressIndicator()
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                ItemRowforNotification(context, item = item)
            }
        }
    }
}

@Composable
fun ShowProgressIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}


@Composable
fun ItemRowforNotification(context: Context, item: NotificationClass) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(id = R.drawable.notification), // Replace with your notification icon
                contentDescription = "Notification",
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.notification_name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(onClick = {
                // Handle delete action here
            }) {
                Icon(
                    imageVector = Icons.Default.Delete, // Replace with your delete icon
                    contentDescription = "Delete",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            val firebasedatabase =
                                FirebaseDatabase
                                    .getInstance()
                                    .getReference("notification")
                                    .child(item.id)
                                    .removeValue()
                                    .addOnSuccessListener {
                                        Toast
                                            .makeText(
                                                context, "Message Deleted Successfully",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                                    .addOnFailureListener {
                                        Toast
                                            .makeText(
                                                context,
                                                "Failed To Delete Message!",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                        }
                )
            }
        }
    }
}
