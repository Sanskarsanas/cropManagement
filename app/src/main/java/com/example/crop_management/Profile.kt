package com.example.crop_management


import android.app.Activity.MODE_PRIVATE
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crop_management.ui.theme.GreenJc
import com.example.crop_management.ui.theme.family

@Composable
fun Profile(activity: ComponentActivity) {

    val context= LocalContext.current.applicationContext

    val editorforName=activity.getSharedPreferences("My_Name_EMail", MODE_PRIVATE)
    var firebase_name = editorforName.getString("name", null)
    var firebase_email = editorforName.getString("email", null)

    val editor1=context.getSharedPreferences("My_Addres", MODE_PRIVATE)
    val profileaddress=editor1.getString("address",null)

    Box(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row{

                Image(
                    imageVector = Icons.Default.Person,
                    contentScale = ContentScale.None,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(30.dp)
                        .size(150.dp)
                        .clip(shape = CircleShape)
                        .background(GreenJc)
                        .padding(20.dp)
                )
            }

            Card(modifier = Modifier
                .padding(top = 10.dp)
                .width(350.dp)
                .height(60.dp), elevation = CardDefaults.elevatedCardElevation(7.dp)){
                Row(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()){
                    Image(imageVector = Icons.Default.Person, contentDescription = "", modifier = Modifier
                        .size(60.dp)
                        .padding(top = 0.dp))
                    Text(text = firebase_name.toString(), fontFamily = family, fontWeight = FontWeight.Bold,modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 4.dp), fontSize = 25.sp)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Card(modifier = Modifier
                .padding(top = 10.dp)
                .width(350.dp)
                .height(60.dp), elevation = CardDefaults.elevatedCardElevation(7.dp)){
                Row(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()){
                    Image(imageVector = Icons.Default.Email, contentDescription = "", modifier = Modifier
                        .size(60.dp)
                        .padding(top = 0.dp))
                    Text(text = firebase_email.toString(), fontFamily = family, fontWeight = FontWeight.Bold,modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 4.dp), fontSize = 25.sp)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Card(modifier = Modifier
                .padding(top = 10.dp)
                .width(350.dp)
                .height(60.dp), elevation = CardDefaults.elevatedCardElevation(7.dp)){
                Row(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()){
                    Image(imageVector = Icons.Default.Settings, contentDescription = "", modifier = Modifier
                        .size(60.dp)
                        .padding(top = 0.dp))
                    Text(text = "Settings", fontFamily = family, fontWeight = FontWeight.Bold,modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 4.dp), fontSize = 25.sp)
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Card(modifier = Modifier
                .padding(top = 10.dp)
                .width(350.dp)
                .height(60.dp), elevation = CardDefaults.elevatedCardElevation(7.dp)){
                Row(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()){
                    Image(imageVector = Icons.Default.LocationOn, contentDescription = "", modifier = Modifier
                        .size(60.dp)
                        .padding(top = 0.dp))
                    Text(text = profileaddress.toString(), fontFamily = family, fontWeight = FontWeight.Bold,modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize()
                        .padding(top = 0.dp), fontSize = 15.sp, maxLines = 7)
                }
            }
            Spacer(modifier = Modifier.height(30.dp))

            Card(modifier = Modifier
                .padding(top = 10.dp)
                .width(350.dp)
                .height(60.dp), elevation = CardDefaults.elevatedCardElevation(7.dp)){
                Row(modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()){
                    Image(painter = painterResource(id = R.drawable.exit2), contentDescription = "", modifier = Modifier
                        .size(60.dp)
                        .padding(top = 0.dp))
                    Text(text = "Log Out", fontFamily = family, fontWeight = FontWeight.Bold,modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 4.dp), fontSize = 25.sp)
                }
            }
        }
    }
}