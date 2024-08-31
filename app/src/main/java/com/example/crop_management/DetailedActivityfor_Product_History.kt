package com.example.crop_management

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import android.annotation.SuppressLint
import coil.compose.rememberAsyncImagePainter
import  androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation

class DetailedActivityfor_Product_History : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val name = intent.getStringExtra("name").toString()
        val price = intent.getStringExtra("price").toString()
        val quantity = intent.getStringExtra("quantity").toString()
        val itemimage = intent.getStringExtra("imageurl").toString()


        setContent {
            ItemDetailScreen(
                name,
                price,
                quantity,
                itemimage
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ItemDetailScreen(
    name: String?,
    price: String?,
    quantity: String?,
    itemimage: String?
) {
    val loading by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = itemimage)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                        placeholder(com.example.crop_management.R.drawable.dowanloading)
                        transformations(CircleCropTransformation())
                    }).build()
            ), contentDescription = "", Modifier.size(250.dp)
        )

        Text(text = "Name : ${name}", fontSize = 30.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Price : ${price}", fontSize = 20.sp, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "Quatity : ${quantity}", fontSize = 20.sp, fontWeight = FontWeight.Normal)
    }
}


