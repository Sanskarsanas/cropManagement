package com.example.crop_management

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.addPathNodes
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.crop_management.api.NetworkResponse
import com.example.crop_management.api.WeatherModel
import com.example.crop_management.ui.theme.GreenJc
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.Locale


//Location MAin Activity
class WeatherPage : ComponentActivity() {

     var address: String?=""

    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        createNotificationChannel(this@WeatherPage)
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
             address = location?.let { getAddressFromLatLng(this, it.latitude, it.longitude) }
            setContent {
                window.statusBarColor = Color.LightGray.toArgb()

                val weatherviewmodel = ViewModelProvider(this)[WeatherViewmodel::class.java]
//                val weatherResult = weatherviewmodel.weatherResult.observeAsState()
                weatherpage(weatherviewmodel, address!!)
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

@Composable
fun weatherpage(viewmodel:WeatherViewmodel,data:String) {

    var city2 = data!!

    val weatherResult = viewmodel.weatherResult.observeAsState()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(bottom = 5.dp),
        horizontalAlignment =
        Alignment.CenterHorizontally
    ) {
        Row(){

            OutlinedTextField(
                value = city2!!,
                shape = RoundedCornerShape(50.dp),
                onValueChange = {
                    city2 = it
                },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                trailingIcon = {
                    Image(
                        modifier = Modifier.clickable {
                            viewmodel.getData(data!!)
                        },
                        imageVector = Icons.Default.Search,
                        contentDescription = ""
                    )
                }
            )
        }

        when(val result=weatherResult.value){
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
               WeatherDetails(result.data)
            }
            null -> {}
        }
    }
}

@Composable
fun WeatherDetails(result: WeatherModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 8.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                tint = GreenJc,
                contentDescription = "Location",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = result.location.name, fontWeight = FontWeight.Bold, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = result.location.country, fontWeight = FontWeight.Normal, fontSize = 27.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "${result.current.temp_c}° C", fontSize = 40.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        AsyncImage(
            model = "https:${result.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = "",
            modifier = Modifier.size(190.dp)
        )
        Text(text = result.current.condition.text, fontSize = 40.sp)
        Spacer(modifier = Modifier
            .height(20.dp)
            .border(2.dp, Color.Black))
        Row {
            Text(text = "Feels Like : ${result.current.feelslike_c}° C", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        }

        Card(elevation = CardDefaults.elevatedCardElevation(5.dp), colors = CardDefaults.cardColors(containerColor = Color.LightGray), modifier = Modifier.size(350.dp)) {
            Column(modifier = Modifier.size(350.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    Column {
                        Text(text = result.current.humidity, fontSize = 23.sp, textAlign = TextAlign.Center)
                        Text(text = "Humidity", fontSize = 20.sp, fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
                    }
                    Column {
                        Text(text = "${result.current.wind_kph}Km/h", fontSize = 23.sp, textAlign = TextAlign.Center)
                        Text(text = "Wind Speed", fontSize = 20.sp, fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
               
                Spacer(modifier = Modifier.height(20.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    Column {
                        Text(text = result.location.localtime.split(" ")[0], fontSize = 23.sp, textAlign = TextAlign.Center)
                        Text(text = "Local time", fontSize = 20.sp, fontWeight = FontWeight.Normal, textAlign = TextAlign.Center)
                    }
                    Column {
                        Text(text = result.location.localtime.split(" ")[1], fontSize = 23.sp, textAlign = TextAlign.Center)
                        Text(
                            text = "Local Date",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal, textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
