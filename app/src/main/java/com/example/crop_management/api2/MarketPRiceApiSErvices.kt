package com.example.crop_management.api2

import retrofit2.http.GET

interface MarketPRiceApiSErvices {
    @GET("market/prices")
    suspend fun getMarketPrices():List<ProductPrice>
}