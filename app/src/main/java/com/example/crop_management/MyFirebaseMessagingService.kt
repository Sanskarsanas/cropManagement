package com.example.crop_management

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        // This is called when the FCM token is updated
        Log.d("FCM", "New token: $token")
        // Send the token to your server or save it as needed
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let {
            // Handle the message and trigger a notification
            sendNotification(it.body ?: "No message content")
        }
    }

    private fun sendNotification(messageBody: String) {
        val notificationBuilder = NotificationCompat.Builder(this, "default")
            .setSmallIcon(R.drawable.notification)
            .setContentTitle("Crop Management")
            .setContentText(messageBody)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0, notificationBuilder.build())
    }
}