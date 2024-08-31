package com.example.crop_management

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import java.time.LocalDate
import java.util.Calendar
import kotlin.math.absoluteValue

class Autoimageslider : ComponentActivity() {
    @OptIn(ExperimentalPagerApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            window.statusBarColor = Color.Transparent.toArgb()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
                    .padding(bottom = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                SHwoitgo()
                AlertScreen(this@Autoimageslider)
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun SHwoitgo() {
    val pagerState = rememberPagerState(initialPage = 0)
    val imageSlider = listOf(
        painterResource(id = R.drawable.cart),
        painterResource(id = R.drawable.organic),
        painterResource(id = R.drawable.plantdiseas)
    )

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(4000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(top = 35.dp)
            .height(580.dp)
            .size(400.dp)
    ) {
        HorizontalPager(
            count = imageSlider.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier.run {
                height(554.dp)
                    .fillMaxWidth()
            }
        ) { page ->
            Card(
                elevation = CardDefaults.cardElevation(5.dp),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .height(700.dp)
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }

                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                Column(
                    modifier = Modifier
                        .size(500.dp)
                        .height(500.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = imageSlider[page],
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(300.dp),
                    )
                }
            }
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(16.dp),
        )
        Spacer(modifier = Modifier.height(100.dp))
        //  AlertScreen(context = LocalContext.current)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertScreen1() {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Default values for the current date
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    // Variables to hold the selected date components
    var selectedYear: Int by remember { mutableStateOf(year) }
    var selectedMonth: Int by remember { mutableStateOf(month + 1) } // Month is 0-indexed
    var selectedDay: Int by remember { mutableStateOf(day) }

    var isbottomopen by remember {
        mutableStateOf(false)
    }

    val textState = remember { mutableStateOf("") }
    val ActionEvent = remember { mutableStateOf("") }

    var selectedDate = LocalDate.of(selectedYear, selectedMonth, selectedDay)
    // Create a DatePickerDialog

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Button to show the DatePickerDialog

        val datePickerDialog = DatePickerDialog(
            context,
            { _, y, m, d ->
                // Update the selected date variables when the user selects a date
                selectedYear = y
                selectedMonth = m + 1 // Convert to 1-indexed month
                selectedDay = d

                // Create a LocalDate object from the selected values
                selectedDate = LocalDate.of(selectedYear, selectedMonth, selectedDay)
            },
            year, month, day
        )

        OutlinedButton(
            onClick = { datePickerDialog.show() },
            Modifier.border(3.dp, Color.Black),
            shape = RoundedCornerShape(30.dp)
        ) {
            Text(text = "Select the Date")
        }

        OutlinedButton(onClick = { isbottomopen = true }) {
            Text(text = "Set The Alert On This Date")
        }

        OutlinedButton(onClick = {
            Intent(context, Autoimageslider::class.java).also {
                context.startActivity(it)
            }
        }) {
            Text(text = "Go TO Image Slider")
        }


        if (isbottomopen) {
            ModalBottomSheet(onDismissRequest = { isbottomopen = false }) {
                // Use a Column to organize the layout
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(300.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // OutlinedTextField with label and placeholder
                    OutlinedTextField(
                        value = selectedDate.toString(),
                        onValueChange = { newText ->
                            textState.value = selectedDate.toString()
                        },
                        label = { Text("Event Date") }
                    )

                    Spacer(
                        modifier = Modifier
                            .height(30.dp)
                            .padding(bottom = 20.dp)
                    )
                    OutlinedTextField(
                        value = ActionEvent.value,
                        onValueChange = { newText ->
                            ActionEvent.value = newText
                        },
                        label = { Text("Event Name") },
                        placeholder = { Text("Eg.. Sowing Wheat Seed") }
                    )

                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun preview() {
    SHwoitgo()
}