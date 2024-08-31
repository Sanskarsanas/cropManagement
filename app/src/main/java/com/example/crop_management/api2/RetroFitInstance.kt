package com.example.crop_management.api2

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroFitInstance {
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.example.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: MarketPRiceApiSErvices by lazy {
        retrofit.create(MarketPRiceApiSErvices::class.java)
    }
}