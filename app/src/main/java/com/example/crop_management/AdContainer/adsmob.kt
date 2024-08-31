package com.example.crop_management.AdContainer

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

@Composable
fun AdmobBanner(modifier: Modifier, activity: Activity){
    AndroidView(modifier = modifier.fillMaxWidth(), factory = {
        AdView(it).apply {
            setAdSize(AdSize.MEDIUM_RECTANGLE)
            adUnitId="ca-app-pub-5732184303859838/2467139093"
            loadAd(AdRequest.Builder().build())
        }
    })
}