package com.example.crop_management.api2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MarketViewModel : ViewModel() {

    private val _marketPrices = MutableStateFlow<List<ProductPrice>>(emptyList())
    val marketPrices: StateFlow<List<ProductPrice>> = _marketPrices

    init {
        viewModelScope.launch {
            try {
                val prices = RetroFitInstance.api.getMarketPrices()
                _marketPrices.value = prices
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}
