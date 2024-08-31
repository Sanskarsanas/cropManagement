package com.example.crop_management

import android.app.Activity.MODE_PRIVATE
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.os.LocaleListCompat
import com.example.crop_management.AdContainer.AdmobBanner
import com.example.crop_management.ui.theme.family

@Composable
fun Home(activity: ComponentActivity) {
    home(activity)
}

@Composable
fun home(activity: ComponentActivity) {

    val editor = activity.getSharedPreferences("My_Fileforeditor", MODE_PRIVATE)
    var name = editor.getString("name", null).toString()

    AppCompatDelegate.setApplicationLocales(
        LocaleListCompat.forLanguageTags(
            name.toString()
        )
    )


    val scrollstate = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(state = scrollstate)
            .fillMaxSize()
            .padding(top = 90.dp, bottom = 90.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier
                        .size(200.dp)
                        .clickable {
                            Intent(activity, Autoimageslider::class.java).also {
                                activity.startActivity(it)
                            }
                        }
                        .padding(10.dp), elevation = 5.dp, shape = RoundedCornerShape(50.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.calendericon),
                            contentDescription = "",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(80.dp)
                                .padding(bottom = 10.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.alerts),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(5.dp),
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(10.dp), shape = RoundedCornerShape(40.dp), elevation = 5.dp
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.plant_disease),
                            tint = Color.Unspecified,
                            contentDescription = "",
                            modifier = Modifier.size(80.dp)
                        )
                        Text(
                            text =stringResource(id = R.string.disease_detector),
                            textAlign = TextAlign.Center,
                            fontSize = 22.sp,
                            modifier = Modifier.padding(5.dp),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Card(
                    modifier = Modifier
                        .size(200.dp)
                        .padding(10.dp), elevation = 5.dp, shape = RoundedCornerShape(40.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.market_live_prices),
                            contentDescription = "",
                            tint = Color.Unspecified,
                            modifier = Modifier.size(60.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.liveproduct_price),
                            fontSize = 22.sp,
                            modifier = Modifier.padding(5.dp),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Card(modifier = Modifier
                    .size(200.dp)
                    .clickable {
                        Intent(activity, WeatherPage::class.java).also {
                            activity.startActivity(it)
                        }
                    }
                    .padding(10.dp), shape = RoundedCornerShape(40.dp), elevation = 5.dp) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.weather2),
                            tint = Color.Unspecified,
                            contentDescription = "",
                            modifier = Modifier
                                .size(90.dp)
                                .padding(bottom = 25.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.todaysweather),
                            fontSize = 22.sp,
                            modifier = Modifier.padding(5.dp),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(250.dp)
            ) {
                AdmobBanner(modifier = Modifier, activity = activity)
            }
            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .padding(20.dp)
                    .clickable {
                        Intent(activity, SellYourProducts::class.java).also {
                            activity.startActivity(it)
                            activity.finish()
                        }
                    },
                elevation = 6.dp,
                shape = RoundedCornerShape(40.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cart),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(start = 10.dp, top = 30.dp)
                            .size(80.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.sellyourproducts), fontFamily = family,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = 15.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Moveit() {
   // home()
}