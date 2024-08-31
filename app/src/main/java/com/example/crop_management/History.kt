package com.example.crop_management

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.crop_management.ui.theme.Crop_ManagementTheme
import com.google.firebase.database.FirebaseDatabase

class History : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ItemListScreenforHistory()
        }
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")
@Composable
fun ItemListScreenforHistory() {

    val viewModel: ViewModelFor_Product_History = viewModel()

    val items by viewModel.items.collectAsState()
    val context = LocalContext.current

    if(items.isEmpty()){
        ShownoData()
    }
    else{
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { item ->
                ItemRowforHistory(context, item = item){
                    openDetailActivityforHistory(context,item)
                }
            }
        }
    }
}

fun openDetailActivityforHistory(context: Context, item: SellProduct) {
    val intent = Intent(context, DetailedActivityfor_Product_History::class.java).apply {
        putExtra("name", item.name.toString())
        putExtra("price", item.price.toString())
        putExtra("quantity", item.quantity.toString())
        putExtra("imageurl", item.imageUrl.toString())
//         Add more item details as needed
    }
    context.startActivity(intent)
}

@Composable
fun ItemRowforHistory(context: Context, item: SellProduct, onItemClick: (SellProduct) -> Unit) {

    var openanimatedbutton by remember {
        mutableStateOf(false)
    }

    var isopenCircularindicator by remember {
        mutableStateOf(true)
    }

    var paint = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current).data(data = item.imageUrl)
            .apply(block = fun ImageRequest.Builder.() {
                crossfade(true)
                transformations(CircleCropTransformation())
            }).build()
    )
    Column(modifier = Modifier.fillMaxSize()) {

        Card(
            elevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick(item) }
                .height(300.dp)
                .size(180.dp),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row() {
                    Image(
                        painter =
                        rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(data = item.imageUrl)
                                .apply(block = fun ImageRequest.Builder.() {
                                    crossfade(true)
                                    placeholder(R.drawable.dowanloading)
                                    transformations(CircleCropTransformation())
                                }).build()
                        ), contentDescription = "", modifier = Modifier.size(160.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.delete),
                        contentDescription = "",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(start = 50.dp)
                            .clickable {
                                val firebasedatabase =
                                    FirebaseDatabase
                                        .getInstance()
                                        .getReference("SellProducts")
                                        .child(item.name)
                                        .removeValue()
                                        .addOnSuccessListener {
                                            Toast
                                                .makeText(
                                                    context, "Product Is Deleted Successfully",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                        .addOnFailureListener {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Failed To Delete Product!",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                            })
                }
                androidx.compose.material.Text(
                    text = item.name,
                    style = MaterialTheme.typography.h6
                )
                androidx.compose.material.Text(
                    text = item.quantity,
                    style = MaterialTheme.typography.h6
                )
                androidx.compose.material.Text(
                    text = item.price,
                    style = MaterialTheme.typography.h6
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                // Additional FABs
                AnimatedVisibility(visible = openanimatedbutton) {
                    Column {
                        FloatingActionButton(
                            onClick = {
                                openanimatedbutton=!openanimatedbutton
                            },
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            androidx.compose.material.Text(text = "Low \nPrice")
                        }
                        Spacer(modifier = Modifier.height(10.dp))

                        FloatingActionButton(
                            onClick = {
                                openanimatedbutton=!openanimatedbutton
                            },
                            modifier = Modifier.padding(bottom = 16.dp)
                        ) {
                            androidx.compose.material.Text(text = "High \nPrice")
                        }
                    }
                }

                // Main FAB
                FloatingActionButton(
                    onClick = { openanimatedbutton = !openanimatedbutton},
                    modifier = Modifier
                ) {
                    Icon(Icons.Default.Add, contentDescription = "")
                }
            }
        }
    }
}
@Composable
fun ShownoData() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        androidx.compose.material.CircularProgressIndicator()
    }
}
