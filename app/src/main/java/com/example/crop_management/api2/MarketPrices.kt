package com.example.crop_management.api2

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crop_management.ViewModelFor_Product_History
import com.example.crop_management.ui.theme.Crop_ManagementTheme

class MarketPrices : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarketPricesScreen()
        }
    }
}
@Composable
fun MarketPricesScreen() {

    val viewModel: MarketViewModel = viewModel()

    val items by viewModel.marketPrices.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().background(Color.LightGray)){
        items(items) { product ->
            Text(text = "${product.ProductName}: \$${product.productPrice}")
        }
    }
}