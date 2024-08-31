//package com.example.crop_management
//
//import android.content.Intent
//import android.content.res.Configuration
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.app.AppCompatDelegate
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Button
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.TextRange
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.core.os.LocaleListCompat
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.crop_management.ui.theme.Crop_ManagementTheme
//import com.example.crop_management.ui.theme.GreenJc
//import io.ktor.util.converters.DataConversion
//import java.util.Locale
//
//class MultilanguaglApplication : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(GreenJc),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                androidx.compose.material.Text(text = stringResource(id = R.string.subscibe))
//                androidx.compose.material.Text(text = stringResource(id = R.string.boy))
//                androidx.compose.material.Text(text = stringResource(id = R.string.girl))
//                androidx.compose.material.Text(text = stringResource(id = R.string.hello))
//                Button(onClick = {
//                   AppCompatDelegate.setApplicationLocales(
//                       LocaleListCompat.forLanguageTags(
//                           "hi"
//                       )
//                   )
//                    val editor=getSharedPreferences("My_Fileforeditor",MODE_PRIVATE).edit()
//                    editor.putString("name","hi")
//                    editor.apply()
//                }) {
//                    androidx.compose.material.Text(text = "Change The Application Language to hindi")
//                }
//                Spacer(modifier = Modifier.height(20.dp))
//                Button(onClick = {
//                    AppCompatDelegate.setApplicationLocales(
//                        LocaleListCompat.forLanguageTags(
//                                "en"
//                        )
//                    )
//                    val editor=getSharedPreferences("My_Fileforeditor",MODE_PRIVATE).edit()
//                    editor.putString("name","hi")
//                    editor.apply()
//                }) {
//                    androidx.compose.material.Text(text = "Change The Application Language to English")
//                }
//                Spacer(modifier = Modifier.height(20.dp))
//                Button(onClick = {
//                    AppCompatDelegate.setApplicationLocales(
//                        LocaleListCompat.forLanguageTags(
//                            "mr"
//                        )
//                    )
//                    val editor=getSharedPreferences("My_Fileforeditor",MODE_PRIVATE).edit()
//                    editor.putString("name","mr")
//                    editor.apply()
//                 //   viewmodel.update_Data("mr")
//                }) {
//                    androidx.compose.material.Text(text = "Change The Application Language to English")
//                }
//                Button(onClick = {
//                   val intent=Intent(this@MultilanguaglApplication,MultilangActivity2::class.java)
//                       startActivity(intent)
//                }) {
//                    androidx.compose.material.Text(text = "Go To Next Activity")
//                }
//                Button(onClick = {
//                    val intent=Intent(this@MultilanguaglApplication,MainActivity::class.java)
//                    startActivity(intent)
//                }) {
//                    androidx.compose.material.Text(text = "Go To Next HomeActivity")
//                }
//            }
//        }
//    }
//}
