package com.example.pizzapp

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.lazy.LazyColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var isValidEmail by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var isValidPassword by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var nombreUsuario by remember { mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(236, 83, 76))) {
        Column(
            Modifier
                .align(Alignment.Center)
                .padding(11.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(249, 238, 201)),
            ) {
                Column(
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                ) {
                    ImageRegister(navController)
                    Nombre(nombre = nombre, nombreChange = {
                        nombre = it})
                    Apellido(apellido = apellido, apellidoChange = {
                        apellido = it})
                    Email(
                        email = email,
                        emailChange = {
                            email = it
                            isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        },
                        isValidEmail
                    )
                    NombreUsuario(
                        nombreUsuario = nombreUsuario,
                        nombreUsuarioChange ={
                            nombreUsuario = it}
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
                    buttonRegister(
                        context = context,
                        isValidEmail = isValidEmail,
                        isValidPassword = isValidPassword
                    )
                    youHaveAcount(navController = navController)
                }
            }
        }
    }
}


@Composable
fun ImageRegister(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("inicio") },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(130.dp), // Ajusta este valor para cambiar el tamaño del logo
            painter = painterResource(id = R.drawable.logoregistro),
            contentDescription = null,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Nombre(
    nombre:String,
    nombreChange: (String)->Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            value = nombre,
            onValueChange = nombreChange,
            label = { Text("Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Apellido(
    apellido:String,
    apellidoChange: (String)->Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            value = apellido,
            onValueChange = apellidoChange,
            label = { Text("apellido") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NombreUsuario(
    nombreUsuario:String,
    nombreUsuarioChange: (String)->Unit,
    //isValidNombreUsuario: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            value = nombreUsuario,
            onValueChange = nombreUsuarioChange,
            label = {Text("Nombre de usuario")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            singleLine = true,

        )
    }
}

@Composable
fun youHaveAcount(navController: NavController){
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Text(
            text = "¿Ya tienes una cuenta?",
            color = Color.Black,
            modifier = Modifier
                .padding(5.dp)
        )

        Text(
            text = "Iniciar sesión",
            color = Color.Blue,
            modifier = Modifier
                .padding(5.dp)
                .clickable {
                    navController.navigate("login")
                }
        )
    }

}

@Composable
fun buttonRegister(
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
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth(),
            onClick = {Register(context)},
            //enabled = isValidEmail && isValidPassword
        ) {
            Text(text = "Registrarse")
        }
    }
}
fun Register(context: Context){
    Toast.makeText(context, "Aca se hace la funcionalidad", Toast.LENGTH_LONG).show()
}
