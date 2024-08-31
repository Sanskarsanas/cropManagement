package com.example.crop_management

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.crop_management.ui.theme.Crop_ManagementTheme
import com.google.firebase.auth.FirebaseAuth

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp(){
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MyApp(onGoogleSignInClicked: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Firebase Authentication") }
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = onGoogleSignInClicked,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Sign In with Google")
            }

            Spacer(modifier = Modifier.height(20.dp))

            LogoutButton()
        }
    }
}

@Composable
fun LogoutButton() {
    val auth = FirebaseAuth.getInstance()

    Button(
        onClick = {
            auth.signOut() // Log out user
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Log Out")
    }
}
