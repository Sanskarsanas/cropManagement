//package com.example.crop_management
//
//import android.content.Context
//import android.net.Uri
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.layout.Column
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import com.example.crop_management.api2.RetroFitInstance.api
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import okhttp3.MediaType
//import okhttp3.MediaType.Companion.toMediaTypeOrNull
//import okhttp3.MultipartBody
//import okhttp3.RequestBody
//
//class Plant_Disease_Detector : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//
//            val pickImageLauncher = rememberLauncherForActivityResult(
//                contract = ActivityResultContracts.GetContent(),
//                onResult = { uri ->
//                    uri?.let { imageUri ->
//                        // Once the image is selected, send it to the API uploadImageToApi(this@Plant_Disease_Detector, imageUri)
//                    }
//                }
//            )
//
//            Button(onClick = { pickImageLauncher.launch("image/*") }) {
//                Text(text = "Select Image")
//            }
//         //   DisplayDiseaseInfo(diseaseResponse = )
//        }
//    }
//}
//
//fun uploadImageToApi(context: Context, imageUri: Uri) {
//    val contentResolver = context.contentResolver
//    val inputStream = contentResolver.openInputStream(imageUri)
//    val requestFile = inputStream?.readBytes()?.let {
//        RequestBody.create(MediaType.parse("image/*"), it)
//    } ?: return
//    val body = MultipartBody.Part.createFormData("file", "image.jpg", requestFile)
//
//    CoroutineScope(Dispatchers.IO).launch {
//        try {
//            val response = api.detectDisease(body)
//            if (response.isSuccessful) {
//                val diseaseInfo = response.body()
//                withContext(Dispatchers.Main) {
//                    // Update UI with diseaseInfo
//                }
//            } else {
//                Log.e("API Error", response.errorBody()?.string() ?: "Unknown error")
//            }
//        } catch (e: Exception) {
//            Log.e("API Exception", e.localizedMessage ?: "Unknown exception")
//        }
//    }
//}
//
//
//@Composable
//fun DisplayDiseaseInfo(diseaseResponse: DiseaseResponse?) {
//    diseaseResponse?.let {
//        Column {
//            Text(text = "Disease Name: ${it.diseaseName}")
//            Text(text = "Description: ${it.description}")
//            Text(text = "Treatment: ${it.treatment}")
//        }
//    }
//}
//
