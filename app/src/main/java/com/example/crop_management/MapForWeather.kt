package com.example.crop_management

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import com.example.crop_management.ui.theme.GreenJc
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale

//Location MAin Activity
class MapForWeather : ComponentActivity() {

    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        createNotificationChannel(this@MapForWeather)
        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    fetchLocation(this)
                } else {
                    window.statusBarColor = GreenJc.toArgb()
                    setContent {
                        window.statusBarColor = GreenJc.toArgb()
                        AddressScreen(address = "Permission not granted")
                    }
                }
            }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fetchLocation(this)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @Composable
    fun AddressScreen(address: String) {
        setContent {
            window.statusBarColor = GreenJc.toArgb()
            Box() {
                Text(text = "Address Is Not Access")
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun fetchLocation(activity: ComponentActivity) {
        locationClient.lastLocation.addOnSuccessListener { location ->
            val address = location?.let { getAddressFromLatLng(this, it.latitude, it.longitude) }
            setContent {
                window.statusBarColor = GreenJc.toArgb()
                Text(text = address!!)
            }
        }
    }

    private fun getAddressFromLatLng(
        context: Context,
        latitude: Double,
        longitude: Double
    ): String {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses: MutableList<Address>? = geocoder.getFromLocation(latitude, longitude, 1)

        return if (addresses?.isNotEmpty() == true) {
            val address = addresses[0]
            // Extract the locality (e.g., city or neighborhood) or admin area (e.g., state or province)
            address.locality ?: address.subAdminArea ?: address.adminArea ?: "Region not found"
        } else {
            "Address not found"
        }
    }
}