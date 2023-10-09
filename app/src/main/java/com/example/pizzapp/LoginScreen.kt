package com.example.pizzapp

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var isValidEmail by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var isValidPassword by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFF9EEC9))) {
        Column(
            Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth()) {
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .background(Color(0xFFF9EEC9)),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    ImageLogin()
                    Email(
                        email = email,
                        emailChange = {
                            email = it
                            isValidEmail = true
                        }
                    )
                    password(
                        password = password,
                        passwordChange = {
                            password = it
                            isValidPassword = true
                        }

                    )
                    buttonLogin(
                        context = context,
                        isValidEmail = isValidEmail,
                        isValidPassword = isValidPassword
                    )
                }
            }
        }
    }
}


@Composable
fun ImageLogin(){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center) {
        Image(
            modifier = Modifier.width(100.dp),
            painter = painterResource(id = R.drawable.person),
            contentDescription = null,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email(
    email:String,
    emailChange: (String)->Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(value = email, onValueChange = emailChange)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun password(
    password: String,
    passwordChange: (String)->Unit
){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(value = password, onValueChange = passwordChange)
    }

}

@Composable
fun buttonLogin(
    context: Context,
    isValidEmail: Boolean,
    isValidPassword: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            enabled = isValidEmail && isValidPassword
        ) {
            Text(text = "Iniciar sesion")
        }
    }
}
