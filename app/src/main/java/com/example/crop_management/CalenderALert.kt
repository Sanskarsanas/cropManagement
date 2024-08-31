package com.example.crop_management

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.crop_management.ui.theme.GreenJc
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.UUID
import kotlin.script.dependencies.ScriptDependenciesResolver

class CalenderALert : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val context= LocalContext.current

            AlertScreen(this@CalenderALert)

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertScreen(context: ComponentActivity) {

    val calendar = Calendar.getInstance()
    //For sending notification exact time


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
    var ActionEvent = remember { mutableStateOf("") }

    var selectedDate = LocalDate.of(selectedYear, selectedMonth, selectedDay)
    selectedDate.atTime(0, 0)


    var delayInMinutes by remember { mutableStateOf("") }


    var dateinminutes by remember {
        mutableStateOf("")
    }
    
    var text= stringResource(id = R.string.alertset).toString()

    Column(
        modifier = Modifier.size(300.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Button to show the DatePickerDialog

        Autoimageslider()
        val datePickerDialog = DatePickerDialog(
            context,
            { _, y, m, d ->
                // Update the selected date variables when the user selects a date
                selectedYear = y
                selectedMonth = m + 1 // Convert to 1-indexed month
                selectedDay = d

               if (selectedDay.toString() == LocalDate.now().dayOfMonth.toString() && selectedYear.toString()
                        .isEmpty() && selectedMonth.toString().isEmpty() && selectedMonth.toString()==LocalDate.now().month.value.toString()
                ) {
                    Toast.makeText(context,"Don't Select Past Date",Toast.LENGTH_SHORT).show()
                    isbottomopen = false
                } else {
                    isbottomopen = true
                }

            },
            year, month, day
        )

        OutlinedButton(
            onClick = { datePickerDialog.show() },
            border = BorderStroke(2.dp, Color.Black),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = GreenJc)
        ) {
            Text(
                text = stringResource(id = R.string.selectDate),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .border(
                        0.dp,
                        GreenJc
                    )
                    .padding(10.dp)
            )
        }

        Spacer(modifier = Modifier.height(17.dp))

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
                    label = { Text(stringResource(id = R.string.eventname)) },
                    placeholder = { Text(stringResource(id = R.string.eventexmple)) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = {

                        dateinminutes = DateToHoursConverterCalender(selectedDate).toString()

                        delayInMinutes = dateinminutes
                        val delayInMillis = (delayInMinutes.toLongOrNull() ?: 0L) * 60 * 1000

                        // calling the schedulTask Function
                        scheduleTask(context, delayInMillis,ActionEvent.value.toString())

                        Toast.makeText(context,text, Toast.LENGTH_SHORT)
                            .show()
                        Intent(context, MainActivity::class.java).also {
                            context.startActivity(it)
                        }
                    },
                    border = BorderStroke(2.dp, Color.Red),
                    shape = RoundedCornerShape(40.dp)
                ) {
                    Text(text = stringResource(id = R.string.setalertButton))
                }
            }
        }
    }
}

fun DateToHoursConverterCalender(selectedDate:LocalDate):Long{

    var dateText = selectedDate.toString()

    var hoursDiff = 0L

    // Date formatter for parsing the string
    val formatter = DateTimeFormatter.ofPattern("yyyy-M-d")


    //call calculatehoursincenow and return value of time since today midnight 12 am
    val time = calculateHoursSinceStartOfDayCalender()

    val selectedDate = LocalDate.parse(dateText, formatter)

    // Get current date
    val currentDate = LocalDate.now()

    // Calculate the difference in hours
    val hoursBetween = ChronoUnit.HOURS.between(currentDate.atStartOfDay(), selectedDate.atStartOfDay())

    // Update state with the hours difference

    if(hoursBetween>time){
        hoursDiff = hoursBetween - time
    }else{
        hoursDiff = time - hoursBetween
    }

    // this will convert hours into minutes ok
    val notificationmindelay=hoursDiff*60

    return notificationmindelay
}

fun calculateHoursSinceStartOfDayCalender(): Long {
    // Get the current time (e.g., 4 PM)
    val now = LocalDateTime.now()

    // Get the start of the day (12 AM, or midnight)
    val startOfDay = LocalDate.now().atStartOfDay()

    // Calculate the number of hours between start of the day and the current time
    return ChronoUnit.HOURS.between(startOfDay, now)
}

