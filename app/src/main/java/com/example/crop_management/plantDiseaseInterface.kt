//package com.example.crop_management
//
//import com.google.android.gms.common.api.Response
//import okhttp3.MultipartBody
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.http.Multipart
//import retrofit2.http.POST
//import retrofit2.http.Part
//
//interface PlantDiseaseApi {
//    @Multipart
//    @POST("plant/disease/detect")  // Ensure this is the correct endpoint
//    suspend fun detectDisease(
//        @Part image: MultipartBody.Part
//    ): Response<DiseaseResponse>
//}
//// Retrofit setup
//val retrofit = Retrofit.Builder()
//    .baseUrl("https://perenual.com/api/")  // Replace with actual base URL
//    .addConverterFactory(GsonConverterFactory.create())
//    .build()
//
//val api = retrofit.create(PlantDiseaseApi::class.java)
