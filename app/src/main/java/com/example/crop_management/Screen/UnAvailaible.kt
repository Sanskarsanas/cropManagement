package com.example.crop_management.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.crop_management.R
import com.example.crop_management.ui.theme.GreenJc

@Composable
fun UnavailableScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        val composition by rememberLottieComposition(
            spec = LottieCompositionSpec.RawRes(
                resId = R.raw.lottienointernetanimation
            )
        )

        LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever)
        
        Spacer(modifier = Modifier.height(150.dp))
        Text(text = "Please Turn On Internet", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = GreenJc)
    }
}