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
import androidx.compose.material3.Button
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzapp.User
import com.example.pizzapp.decodeJWT
import com.example.pizzapp.navbar.Navbar
import com.google.gson.Gson


@Composable
fun MyProfileScreen(navController: NavController,jwtToken: String) { // Asume que el token JWT es pasado como argumento
    val context = LocalContext.current
    val userData = decodeJWT(jwtToken) // Decodifica la información del usuario desde el token JWT

    // Variables para almacenar los datos del usuario
    var email by remember { mutableStateOf(userData?.get("email") as? String ?: "") }
    var isValidEmail by remember { mutableStateOf(false) }
    var nombreUsuario by remember { mutableStateOf(userData?.get("nombreUsuario") as? String ?: "") }
    var isValidPassword by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf(userData?.get("nombre") as? String ?: "") }
    var apellido by remember { mutableStateOf(userData?.get("apellido") as? String ?: "") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(144, 167, 76))
    ) {
        Navbar(navController)
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Mi Perfil", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            ActualizarTexto("Nombre", nombre, onValueChange = { nombre = it })
            ActualizarTexto("Apellido", apellido, onValueChange = { apellido = it })
            ActualizarTexto("Nombre de Usuario", nombreUsuario, onValueChange = { nombreUsuario = it })
            ActualizarTexto("Email", email, onValueChange = { email = it })


            ButtonUpdate(context = context,
                isValidEmail = isValidEmail,
                isValidPassword = isValidPassword,
                email = email,
                nombre = nombre,
                apellido = apellido,
                nombreUsuario = nombreUsuario,
                navController = navController
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActualizarTexto(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun ButtonUpdate(
    context: Context,
    isValidEmail: Boolean,
    isValidPassword: Boolean,
    email: String,
    nombre: String,
    apellido: String,
    nombreUsuario: String,
    navController: NavController,

){

    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                if (isValidEmail && isValidPassword) {
                    val updatedData = mapOf(
                        "nombre" to nombre,
                        "apellido" to apellido,
                        "correo" to email,
                        "nombreUsuario" to nombreUsuario

                    )
                }
            }
        ) {
            Text(text = "Actualizar")
        }

    }
}
