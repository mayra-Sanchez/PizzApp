package com.example.pizzapp.Screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzapp.R
import kotlinx.coroutines.delay


@Composable
fun ResetPassword(navController: NavController){

    val context = LocalContext.current

    var password2 by remember { mutableStateOf("") }
    var isValidPassword2 by remember { mutableStateOf(false) }

    var code by remember { mutableStateOf(" ") }
    var isValidCode by remember { mutableStateOf(false) }

    var newCode by remember {
        mutableStateOf(" ")
    }

    var isValidNewCode by remember {
        mutableStateOf(false)
    }

    var passwordVisible2 by remember { mutableStateOf(false) }

    var isButtonEnabled by remember { mutableStateOf(false) }
    var isInputEnabled by remember { mutableStateOf(true) }
    var isInputPasswordEnabled by remember { mutableStateOf(true) }
    var isButtonPasswordEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = isButtonEnabled) {
        if (!isButtonEnabled && isInputEnabled && isInputPasswordEnabled && isButtonPasswordEnabled) {
            delay(15 * 60 * 1000)
//            delay(10000) //Para un segundo
            isButtonEnabled = true
            isInputEnabled = false
            isInputPasswordEnabled = false
            isButtonPasswordEnabled = false
        }
    }

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
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(249, 238, 201)),
            ) {
                Column(
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)) {
                    Image()
                    verifityCode(code = code, codeChange = {
                        code = it}, isValidCode = isValidCode, isInputEnabled = isInputEnabled)
                    NewPassword(
                        isInputPasswordEnabled = isInputPasswordEnabled,
                        password = password2,
                        passwordChange = {
                            password2 = it
                            isValidPassword2 = password2.length >= 6
                        },
                        passwordVisible = passwordVisible2,
                        passwordVisibleChange = { passwordVisible2 = !passwordVisible2 },
                        isValidPassword = isValidPassword2
                    )
                    updatePassword(
                        isButtonPasswordEnabled = isButtonPasswordEnabled,
                        context = context,
                        isValidPassword = isValidPassword2,
                        password2 = password2,
                        navController = navController
                    )
                    sendCodeAgain(navController = navController, isButtonEnabled = isButtonEnabled,
                        onSendCodeClicked = {
                        isButtonEnabled = false
                    } )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPassword(
    isInputPasswordEnabled: Boolean,
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
            enabled = isInputPasswordEnabled,
            label  = { Text("Contraseña") },
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
                PasswordVisibility2(
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityToggle = passwordVisibleChange
                )
            }
        )
    }
}


@Composable
fun PasswordVisibility2(
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun verifityCode(
    code: String,
    codeChange: (String)->Unit,
    isValidCode: Boolean,
    isInputEnabled: Boolean
){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            value = code,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = codeChange,
            label = { Text("Código") },
            singleLine = true,
            maxLines = 1,
            enabled = isInputEnabled
        )
    }
}

@Composable
fun updatePassword(
    isButtonPasswordEnabled: Boolean,
    context: Context,
    isValidPassword: Boolean,
    password2: String,
    navController: NavController
    ){
    Row(horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
    ){
        androidx.compose.material3.Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth(),
            enabled = isButtonPasswordEnabled,
            onClick = {
                if (isValidPassword) {
                    Toast.makeText(context, "Contraseña actualizada", Toast.LENGTH_SHORT).show()
                    navController.navigate("login")

                } else {
                    Toast.makeText(context, "No se pudo actualizar la contraseña", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Cambiar contraseña")
        }


    }
}

@Composable
fun sendCodeAgain(navController: NavController, isButtonEnabled: Boolean, onSendCodeClicked: () -> Unit){
    androidx.compose.material3.Button(
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            if (isButtonEnabled){
                onSendCodeClicked()
            }
            navController.navigate("forgot-password")
        },
        enabled = isButtonEnabled
    ) {
        Text(text = "Mandar código otra vez")
    }

}
