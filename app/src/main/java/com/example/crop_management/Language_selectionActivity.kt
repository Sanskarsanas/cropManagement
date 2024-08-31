package com.example.crop_management

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.example.crop_management.ui.theme.Crop_ManagementTheme

class Language_selectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LanguageFun(this@Language_selectionActivity)
        }
    }
}

@Composable
fun LanguageFun(activity: ComponentActivity) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(id = R.string.selectlanguage),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Card(modifier = Modifier.fillMaxSize().padding(30.dp), elevation = CardDefaults.cardElevation(5.dp)){
            Text(text = stringResource(id = R.string.mar), modifier = Modifier.clickable {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(
                        "mr"
                    )
                )
                val editor = activity.getSharedPreferences("My_Fileforeditor", MODE_PRIVATE).edit()
                editor.putString("name", "mr")
                editor.apply()
                Intent(activity,MainActivity::class.java).also {
                    activity.startActivity(it)
                }
                activity.finish()
            })
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = stringResource(id = R.string.hin), modifier = Modifier.clickable {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(
                        "hi"
                    )
                )
                val editor = activity.getSharedPreferences("My_Fileforeditor", MODE_PRIVATE).edit()
                editor.putString("name", "hi")
                editor.apply()
                Intent(activity,MainActivity::class.java).also {
                    activity.startActivity(it)
                }
                activity.finish()
            })
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = stringResource(id = R.string.eng), modifier = Modifier.clickable {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(
                        "en"
                    )
                )
                val editor = activity.getSharedPreferences("My_Fileforeditor", MODE_PRIVATE).edit()
                editor.putString("name", "en")
                editor.apply()
                Intent(activity,MainActivity::class.java).also {
                    activity.startActivity(it)
                }
                activity.finish()
            })
        }
    }
}