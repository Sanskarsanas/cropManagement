package com.example.crop_management

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.material.TopAppBar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.database.FirebaseDatabase
import java.util.UUID

class WorkManagerYes1(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {

        val message = inputData.getString("message") ?: "Default message"

        showNotification(
            applicationContext,
            "Crop Management Reminder", message
        )
        return Result.success()
    }
}

    // Method to show a simple notification
    @SuppressLint("MissingPermission")
    fun showNotification(context: Context, title: String, message: String) {

        val channelId = "default_channel"

        // Create notification channel for Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        // Build and display the notification
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val databaref=FirebaseDatabase.getInstance().getReference("notification")

        val ID=UUID.randomUUID().toString()
        val notificationclass=NotificationClass(message,ID)
        databaref.child(ID).setValue(notificationclass).addOnSuccessListener {

        }.addOnFailureListener{
            Toast.makeText(context, "Failed to Add Message To Notification", Toast.LENGTH_SHORT)
                .show()
        }
        with(NotificationManagerCompat.from(context)) {
            notify(1, notificationBuilder.build())
        }


    }
