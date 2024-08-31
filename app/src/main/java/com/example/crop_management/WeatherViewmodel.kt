package com.example.crop_management

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.crop_management.api.NetworkResponse
import com.example.crop_management.api.RetrofitInstance
import com.example.crop_management.api.WeatherModel
import com.example.crop_management.api.constant
import com.google.android.gms.common.util.Strings
import kotlinx.coroutines.launch
import okhttp3.Response

class WeatherViewmodel:ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _WeatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _WeatherResult
    fun getData(city: String) {
        _WeatherResult.value = NetworkResponse.Loading
        viewModelScope.launch {
            try {
                val response = weatherApi.getweather(constant.apikey, city)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _WeatherResult.value = NetworkResponse.Success(it)
                    }
                } else {
                    _WeatherResult.value = NetworkResponse.Error("Failed to Load Data")
                }
            }catch (e:Exception){
                _WeatherResult.value=NetworkResponse.Error("Failed to Load Data")
            }
        }
    }
}