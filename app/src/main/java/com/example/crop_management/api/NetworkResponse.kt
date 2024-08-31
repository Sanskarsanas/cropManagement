package com.example.crop_management.api

import com.example.crop_management.Screens

//It referes to Weathermodel
sealed class NetworkResponse<out T>{
    data class Success<out T>(val data:T) : NetworkResponse<T>()
    data class Error(val message:String) : NetworkResponse<Nothing>()
    object Loading:NetworkResponse<Nothing>()
}