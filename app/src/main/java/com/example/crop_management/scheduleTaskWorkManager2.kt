package com.example.crop_management

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

fun scheduleTask(context: Context, delayInMillis: Long, message: String) {

    //for sending the message to the WorkManager through below concept
    val inputData = Data.Builder()
        .putString("message", message)
        .build()
    // Create a one-time work request with a delay
    val workRequest = OneTimeWorkRequestBuilder<WorkManagerYes1>()
        .setInputData(inputData)
        .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
        .build()

    // Enqueue the work request
    WorkManager.getInstance(context).enqueue(workRequest)
}
