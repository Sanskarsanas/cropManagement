package com.example.crop_management

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.MODE_PRIVATE
import android.app.NotificationChannel
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventStart
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationManagerCompat.NotificationWithIdAndTag
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.crop_management.ui.theme.GreenJc
import com.example.crop_management.ui.theme.family
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import java.util.*



//Location MAin Activity
class MainActivity : AppCompatActivity() {

    private lateinit var locationClient: FusedLocationProviderClient
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        createNotificationChannel(this@MainActivity)
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

                val editorforName=getSharedPreferences("passnameandEmail", MODE_PRIVATE)
               val name = editorforName.getString("name",null).toString()
               val email = editorforName.getString("email",null).toString()
                MainScreen(address = address ?: "Location not available", this@MainActivity,name,email)
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
            "${address.getAddressLine(0)}" // Format this string as needed

//            val addressShort = addresses[0]
//            // Extract the locality (e.g., city or neighborhood) or admin area (e.g., state or province)
//            address.locality ?: address.subAdminArea ?: address.adminArea ?: "Region not found"
        } else {
            "Address not found"
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RememberReturnType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(address: String, activity: ComponentActivity,name:String,email:String) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    var navController = rememberNavController()


    //for the passing the langauge

    val editorforlang = activity.getSharedPreferences("My_Fileforeditor", MODE_PRIVATE)
    val language=editorforlang.getString("name", null).toString()

    AppCompatDelegate.setApplicationLocales(
        LocaleListCompat.forLanguageTags(
            language
        )
    )


    val editor1=activity.getSharedPreferences("My_Addres", MODE_PRIVATE).edit()
    editor1.putString("address",address.toString())
    editor1.apply()

    val editorforName=activity.getSharedPreferences("My_Name_EMail", MODE_PRIVATE).edit()
    editorforName.putString("name",name.toString())
    editorforName.putString("email",email.toString())
    editorforName.apply()



    var openAlertDialogue by remember {
        mutableStateOf(false)
    }

    var Selected = remember {
        mutableStateOf(Icons.Default.Home)
    }

    var n: String = ""
    var e: String = ""

    val editor = activity.getSharedPreferences("My_File", MODE_PRIVATE)
    var firebase_name = editor.getString("name", null)
    var firebase_email = editor.getString("email", null)

    n = firebase_name.toString()
    e = firebase_email.toString()


//    val editorforlanguage = activity.getSharedPreferences("My_Fileforeditor", MODE_PRIVATE)
//    var language = editorforlanguage.getString("name", null).toString()
//
//    AppCompatDelegate.setApplicationLocales(
//        LocaleListCompat.forLanguageTags(
//            language
//        )
//    )

    ModalNavigationDrawer(drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .height(90.dp)
                        .fillMaxWidth()
                        .background(GreenJc),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "")
                        Text(text = name, fontFamily = family)
                        Text(text = email, fontFamily = family)
                    }
                }
                HorizontalDivider()

                Card(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(350.dp)
                        .clickable {
                            coroutineScope.launch {
                                drawerState.close()
                                Intent(
                                    activity,
                                    History::class.java
                                ).also {
                                    activity.startActivity(it)
                                }
                            }
                        }
                        .height(60.dp), elevation = CardDefaults.elevatedCardElevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.transaction_history),
                            contentDescription = "",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(top = 0.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.soldproducthistory), fontFamily = family, fontWeight = FontWeight.Bold, modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 4.dp), fontSize = 25.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(350.dp)
                        .clickable {
                            coroutineScope.launch {
                                drawerState.close()
                                navController.navigate(Screens.Settings.toString())
                            }
                        }
                        .height(60.dp), elevation = CardDefaults.elevatedCardElevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ) {
                        Image(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(top = 0.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.settings), fontFamily = family, fontWeight = FontWeight.Bold, modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 4.dp), fontSize = 25.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(350.dp)
                        .clickable {
                            coroutineScope.launch {
                                drawerState.close()
                                navController.navigate(Screens.Notifications.toString())
                            }
                        }
                        .height(60.dp), elevation = CardDefaults.elevatedCardElevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ) {
                        Image(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(top = 0.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.notification), fontFamily = family,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 4.dp),
                            fontSize = 25.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(350.dp)
                        .clickable {
                            coroutineScope.launch {
                                drawerState.close()
                                navController.navigate(Screens.About_Us.toString())
                            }
                        }
                        .height(60.dp), elevation = CardDefaults.elevatedCardElevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ) {
                        Image(
                            imageVector = Icons.Default.Info,
                            contentDescription = "",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(top = 0.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.aboutus), fontFamily = family, fontWeight = FontWeight.Bold, modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 4.dp), fontSize = 25.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))


                Card(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(350.dp)
                        .clickable {
                            coroutineScope.launch {
                                drawerState.close()
                                openAlertDialogue = true
                            }
                        }
                        .height(60.dp), elevation = CardDefaults.elevatedCardElevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.exit2),
                            contentDescription = "",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(top = 0.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.alerts), fontFamily = family, fontWeight = FontWeight.Bold, modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 4.dp), fontSize = 25.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(350.dp)
                        .clickable {
                            Intent(activity,Language_selectionActivity::class.java).also {
                                activity.startActivity(it)
                                activity.finish()
                            }
                        }
                        .height(60.dp), elevation = CardDefaults.elevatedCardElevation(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.langaugeicon),
                            contentDescription = "",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(top = 0.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.language), fontFamily = family, fontWeight = FontWeight.Bold, modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 4.dp), fontSize = 25.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                HorizontalDivider()
            }
            if (openAlertDialogue) {
                AlertDialog(
                    icon = {
                        Image(
                            imageVector = Icons.Default.Face,
                            contentDescription = "",
                            Modifier.size(40.dp)
                        )
                    },
                    title = { Text(text = "Find Your Doctor", fontFamily = family, fontSize = 14.sp, color = GreenJc) },
                    text = {
                        Text(
                            text = "Are You Sure you Want To Log Out", fontFamily = family,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    titleContentColor = Color.Magenta,
                    textContentColor = Color.DarkGray,
                    tonalElevation = 30.dp,
                    containerColor = Color.White,
                    onDismissRequest = { openAlertDialogue = false },
                    confirmButton = {
                        Button(
                            onClick = {
                                openAlertDialogue = false
                                //Sign Out the User
                                Firebase.auth.signOut()
                                if (Firebase.auth.currentUser == null) {
                                    val intent = Intent(activity, Login::class.java)
                                    activity.startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        activity,
                                        "Failed to Sign Out",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text(text = "Yes", fontFamily = family, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    },
                    dismissButton = {
                        OutlinedButton(
                            onClick = { openAlertDialogue = false },
                            border = BorderStroke(2.dp, Color.Red)
                        ) {
                            Text(text = "No", fontFamily = family, fontWeight = FontWeight.Bold, color = Color.Red)
                        }
                    })
            }
        }) {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = GreenJc
                    ),
                    title = { Text(text = address, fontFamily = family) },
                    navigationIcon = {
                        IconButton(onClick = { coroutineScope.launch { drawerState.open() } }) {
                            Icon(
                                Icons.Default.AccountCircle,
                                contentDescription = "Account",
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    },
                    actions = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_location_pin_24),
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 55.dp)
                                .size(60.dp)
                                .padding(bottom = 40.dp),
                            tint = Color.Red
                        )
                        Text(
                            text = address,
                            modifier = Modifier
                                .height(90.dp)
                                .padding(top = 13.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                    }
                )
            },
            bottomBar = {
                BottomAppBar(containerColor = GreenJc) {
                    IconButton(
                        onClick = {
                            Selected.value = Icons.Default.Home
                            navController.navigate(Screens.Home.toString())
                        },
                        modifier = Modifier.weight(2f)
                    ) {
                        Icon(
                            Icons.Default.Home,
                            contentDescription = "Home",
                            modifier = Modifier.size(40.dp),
                            tint = if (Selected.value == Icons.Default.Home) Color.Black else Color.White
                        )
                    }
                    IconButton(
                        onClick = {
                            Selected.value = Icons.Default.Person
                            navController.navigate(Screens.Profile22.toString())
                        },
                        modifier = Modifier.weight(2f)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            modifier = Modifier.size(40.dp),
                            tint = if (Selected.value == Icons.Default.Person) Color.Black else Color.White
                        )
                    }
                }
            }
        ) {
            NavHost(navController = navController, startDestination = Screens.Home.toString()) {
                composable(Screens.Home.toString()) {
                    Home(activity)
                }
                composable(Screens.Profile22.toString()) {
                    Profile22(activity)
                }
                composable(Screens.Settings.toString()) {
                    Settings(activity)
                }
                composable(Screens.About_Us.toString()) {
                    About_us(activity)
                }
                composable(Screens.Notifications.toString()) {
                    Notifications(activity)
                }
            }
        }
    }
}



