package com.example.crop_management

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageSource
import com.example.crop_management.ui.theme.GreenJc
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.nio.file.attribute.AclEntry.Builder
import java.util.UUID

class SellYourProducts : ComponentActivity() {
    private val viewModel by viewModels<SellProductViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SellProductForm(viewModel,this@SellYourProducts)
        }
    }
}

class SellProductViewModel : ViewModel() {
    private val storageReference = FirebaseStorage.getInstance().reference
    private val databaseReference = FirebaseDatabase.getInstance().getReference("SellProducts")

    fun saveSellProduct(
        name: String,
        price: String,
        quantity: String,
        imageUri: Uri,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val imageFileName = "images/${UUID.randomUUID()}.jpg"
        val imageRef = storageReference.child(imageFileName)

        imageRef.putFile(imageUri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    val SellProduct = SellProduct(name, price, quantity, imageUrl)
                    val SellProductId = databaseReference.push().key ?: return@addOnSuccessListener

                    databaseReference.child(name).setValue(SellProduct)
                        .addOnSuccessListener {
                            onSuccess()
                        }
                        .addOnFailureListener { exception ->
                            onFailure(exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}


@Composable
fun SellProductForm(viewModel: SellProductViewModel,activity: ComponentActivity) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            selectedImageUri = result.data?.data
        }
    }

    val scrollState = rememberScrollState()

    var iscircularbarstart by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
//            .padding(16.dp)
    ) {
//        val paint= AsyncImage(model = selectedImageUri, contentDescription ="")
        if (selectedImageUri == null) {
            Image(
                painter = painterResource(id = R.drawable.organic),
                contentDescription = "",
                modifier = Modifier
                    .size(170.dp)
                    .border(2.dp, Color.Black)
                    .padding(10.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(model = selectedImageUri),
                contentDescription = "",
                modifier = Modifier
                    .size(170.dp)
                    .border(2.dp, Color.Black)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") },
            placeholder = { Text(text = "Enter the Product Name") },
            shape = RoundedCornerShape(40.dp),
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Gray
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            leadingIcon = {
                Image(painter = painterResource(id = R.drawable.product_namerice), contentDescription = "", modifier = Modifier.size(30.dp))
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = price,
            onValueChange = { price = it},
            label = { Text("Price Per Kg") },
            placeholder = { Text(text = "Enter Price In ₹") },
            shape = RoundedCornerShape(40.dp),
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Gray
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = {
                Image(painter = painterResource(id = R.drawable.money_banking), contentDescription = "", modifier = Modifier.size(30.dp))
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = quantity,
            onValueChange = { quantity = it},
            label = { Text("Quantity In Kg") },
            placeholder = { Text(text = "Select Quantity In Kg") },
            shape = RoundedCornerShape(40.dp),
            colors = androidx.compose.material.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Red,
                unfocusedBorderColor = Color.Gray
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = {
                Image(painter = painterResource(id = R.drawable.quantity), contentDescription ="", modifier = Modifier.size(30.dp))
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = {
                val intent = Intent(Intent.ACTION_PICK).apply {
                    type = "image/*"
                }
                launcher.launch(intent)
            },
            modifier = Modifier.padding(top = 14.dp),
            colors = ButtonDefaults.buttonColors(GreenJc),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text("Select Image", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = {
                if (name.isNotEmpty() && price.isNotEmpty() && quantity.isNotEmpty() && selectedImageUri != null) {
                    iscircularbarstart=true
                    viewModel.saveSellProduct(name, "${price} ₹", "${quantity} Kg", selectedImageUri!!,
                        onSuccess = {
                            Toast.makeText(context,"Product saved successfully",Toast.LENGTH_SHORT).show()
                            iscircularbarstart=false
                            Intent(activity,MainActivity::class.java).also {
                                activity.startActivity(it)
                                activity.finish()
                            }
                        },
                        onFailure = { exception ->
                            Toast.makeText(
                                context,
                                "Failed to save SellProduct: ${exception.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                } else {
                    Toast.makeText(
                        context,
                        "Please fill all fields and select an image",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
            modifier = Modifier.padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(GreenJc),
            border = BorderStroke(2.dp, Color.Black)
        ) {
            Text("Save Product", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        if(iscircularbarstart){
            CircularProgressIndicator(
                // below line is used to add padding
                // to our progress bar.
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp),

                // below line is used to add color
                // to our progress bar.
                color = Color.Blue,

                // below line is used to add stroke
                // width to our progress bar.
                strokeWidth = 8.dp,

                // below line is used to add strokeCap
                // to our progress bar.
                strokeCap = StrokeCap.Round
            )
        }
    }
}
