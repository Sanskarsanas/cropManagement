package com.example.crop_management

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.crop_management.Helper.ConnectionStatus
import com.example.crop_management.Helper.currentConnectivityStatus
import com.example.crop_management.Helper.observeConnectivityAsFlow
import com.example.crop_management.Screen.AvailableScreen
import com.example.crop_management.Screen.UnavailableScreen
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class InternetMyFriend: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            checkConnectivityStatus(this@InternetMyFriend)
        }
    }
}
@Composable
fun checkConnectivityStatus(activity: ComponentActivity){

    val connection by connectivityStatus()

    val isConnected = connection === ConnectionStatus.Available

    if(isConnected){
       Intent(activity,MainActivity::class.java).also {
           activity.startActivity(it)
       }
    }
    else{
        UnavailableScreen()
    }
}
@Composable
fun connectivityStatus():State<ConnectionStatus>{
    val Mctx= LocalContext.current

    return produceState(initialValue = Mctx.currentConnectivityStatus) {
        Mctx.observeConnectivityAsFlow().collect{
            value = it
        }
    }
}