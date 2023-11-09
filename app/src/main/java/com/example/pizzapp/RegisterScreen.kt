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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import com.example.pizzapp.models.User

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
                    Password(
                        password = password,
                        passwordChange = {
                            password = it
                            isValidPassword = password.length >= 6

                        },
                        passwordVisible = passwordVisible,
                        passwordVisibleChange = { passwordVisible = !passwordVisible },
                        isValidPassword = isValidPassword
                    )
                    ButtonRegister(
                        context = context,
                        isValidEmail = isValidEmail,
                        isValidPassword = isValidPassword,
                        email = email,
                        password = password,
                        nombre = nombre,
                        apellido = apellido,
                        nombreUsuario = nombreUsuario,
                        navController = navController
                    )
                    YouHaveAccount(navController = navController)
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
            modifier = Modifier.size(140.dp),
            painter = painterResource(id = R.drawable.logoregistro),
            contentDescription = null,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Nombre(
    nombre: String,
    nombreChange: (String) -> Unit
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = nombreChange,
            label = { Text("Nombre") },
            singleLine = true,
            maxLines = 1,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Apellido(
    apellido: String,
    apellidoChange: (String) -> Unit
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
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = apellidoChange,
            label = { Text("Apellido") },
            singleLine = true,
            maxLines = 1,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NombreUsuario(
    nombreUsuario: String,
    nombreUsuarioChange: (String) -> Unit
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
            label = { Text("Nombre de usuario") },
            singleLine = true,
            maxLines = 1,
        )
    }
}

@Composable
fun YouHaveAccount(navController: NavController){
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
fun ButtonRegister(
    context: Context,
    isValidEmail: Boolean,
    isValidPassword: Boolean,
    email: String,
    password: String,
    nombre: String,
    apellido: String,
    nombreUsuario: String,
    navController: NavController

) {
    val firestoreRepository = FirestoreRepository()

    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (isValidEmail && isValidPassword) {
                    val user = User(nombre, apellido, email, nombreUsuario, password)
                    firestoreRepository.createUser(user,
                        onSuccess = {
                            Toast.makeText(context, "Usuario registrado con éxito", Toast.LENGTH_LONG).show()
                            navController.navigate("login")
                        },
                        onFailure = { e ->
                            Toast.makeText(context, "Error al registrar: ${e.message}", Toast.LENGTH_LONG).show()
                        })
                } else {
                    Toast.makeText(context, "Revisa los campos", Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Text(text = "Registrarse")
        }
    }
}
