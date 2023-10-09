package com.example.pizzapp

import android.content.Context
import android.graphics.drawable.Icon
import android.media.Image
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.w3c.dom.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextField
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreen() {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var isValidEmail by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var isValidPassword by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(236, 83, 76))) {
        Column(
            Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .background(Color(249, 238, 201))
                    .clip(RoundedCornerShape(10.dp)),

            ) {
                Column(
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)) {
                    ImageLogin()
                    Email(
                        email = email,
                        emailChange = {
                            email = it
                            isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        },
                        isValidEmail
                    )
                    password(
                        password = password,
                        passwordChange = {
                            password = it
                            isValidPassword = password.length >= 6

                        },
                        passwordVisible = passwordVisible,
                        passwordVisibleChange = { passwordVisible = !passwordVisible },
                        isValidPassword = isValidPassword

                    )
                    buttonLogin(
                        context = context,
                        isValidEmail = isValidEmail,
                        isValidPassword = isValidPassword
                    )
                    dontHaveAcount()
                }
            }
        }
    }
}

@Composable
fun ImageLogin(){
    Row(
        horizontalArrangement = Arrangement.Center) {
        Image(
            modifier = Modifier.size(400.dp),
            painter = painterResource(id = R.drawable.logologin),
            contentDescription = null,
        )
    }
}

@Composable
fun dontHaveAcount(){
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Text(
            text = "¿No tienes una cuenta?",
            color = Color.Black,
            modifier = Modifier
                .padding(5.dp)
        )

        Text(
            text = "Registrate",
            color = Color.Blue,
            modifier = Modifier
                .padding(5.dp)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email(
    email:String,
    emailChange: (String)->Unit,
    isValidEmail: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            value = email,
            onValueChange = emailChange,
            label = {Text("Email")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1,
            singleLine = true,
            colors = if(isValidEmail){
                     TextFieldDefaults.outlinedTextFieldColors(
                         focusedLabelColor = Color.Green,
                         focusedBorderColor = Color.Green)
                     }else{
                    TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Red,
                    focusedBorderColor = Color.Red)
                     },
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun password(
    password: String,
    passwordChange: (String)->Unit,
    passwordVisible: Boolean,
    passwordVisibleChange: () -> Unit,
    isValidPassword: Boolean

){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            visualTransformation = if(passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            shape = RoundedCornerShape(10.dp),
            value = password,
            onValueChange = passwordChange,
            maxLines = 1,
            singleLine = true,
            label  = {Text("Contraseña")},
            colors = if(isValidPassword){
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Green,
                    focusedBorderColor = Color.Green)
            }else{
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Red,
                    focusedBorderColor = Color.Red)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                PasswordVisibilityToggle(
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityToggle = passwordVisibleChange
                )
            }
        )
    }
}

@Composable
fun PasswordVisibilityToggle(
    passwordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit
) {
    val imageResId: Int = if (passwordVisible) {
        R.drawable.visibility_off
    } else {
        R.drawable.visibility
    }
    IconButton(
        onClick = onPasswordVisibilityToggle,
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = imageResId),
            contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
        )
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
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(116, 27, 15)
            ),
            modifier = Modifier.fillMaxWidth(),
            onClick = {Login(context)},
            //enabled = isValidEmail && isValidPassword
        ) {
            Text(text = "Iniciar sesión")
        }
    }
}
fun Login(context: Context){
    Toast.makeText(context, "Aca se hace la funcionalidad", Toast.LENGTH_LONG).show()
}
