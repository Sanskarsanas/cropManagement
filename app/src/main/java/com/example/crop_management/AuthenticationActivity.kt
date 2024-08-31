package com.example.crop_management


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.crop_management.ui.theme.GreenJc
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthenticationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AuthScreen(this@AuthenticationActivity)
        }
    }

    @Composable
    fun AuthScreen(activity:ComponentActivity) {
        var email by remember { mutableStateOf("") }
        var name by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var errorMessage by remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = GreenJc
                ),
                shape = RoundedCornerShape(40.dp),
                placeholder = { Text(text = "Enter Your Name") },
                label = { Text("Name") })
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text(text = "Enter the Email") },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = GreenJc
                ),
                shape = RoundedCornerShape(40.dp),
                label = { Text("Email") })
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text(text = "Enter the Password") },
                label = { Text("Password") },
                shape = RoundedCornerShape(40.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color.Red,
                    unfocusedBorderColor = GreenJc
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(26.dp))

            OutlinedButton(
                onClick = {
                    signIn(email, password, this@AuthenticationActivity)
                },
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Text(
                    "Log In", fontSize = 30.sp, fontWeight = FontWeight.Bold,
                    color = Color.Black, modifier = Modifier
                        .border(0.dp, Color.White)
                        .clickable {
                            SaveData(name,email,password, context = activity)
                        }
                        .padding(6.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {
                    signIn(email, password, this@AuthenticationActivity)
                },
                border = BorderStroke(2.dp, Color.Black),
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                )
            ) {
                Image(painter = painterResource(id = R.drawable.google), contentDescription = "",Modifier.size(24.dp,))
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Sign In",
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .border(0.dp, Color.White)
                        .padding(6.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = MaterialTheme.colors.error)
            }
        }
    }

    //For Saving Data Into Firebase Database
    fun SaveData(name: String, email: String, password: String,context: ComponentActivity) {
        if(name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){

            val database=FirebaseDatabase.getInstance().getReference("User")
            val user =User(name,email,password)

            database.child(name).setValue(user).addOnSuccessListener {
                val intent=Intent(context,MainActivity::class.java).apply {
                    val editorforName=context.getSharedPreferences("passnameandEmail", MODE_PRIVATE).edit()
                    editorforName.putString("name",name.toString())
                    editorforName.putString("email",email.toString())
                    editorforName.apply()
                }
                context.startActivity(intent)

                Toast.makeText(context,"Login Successfully",Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(context,"Failed to Login",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(context,"Please Fill all Attributes!",Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn(email: String, password: String,activity: ComponentActivity) {
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Navigate to MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                   Toast.makeText(activity,"Failed to Create User",Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signUp(email: String, password: String) {
        val auth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // User created successfully, navigate to MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(applicationContext,"Failed to Create User",Toast.LENGTH_SHORT).show()
                }
            }
    }
}
