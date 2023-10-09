package com.example.pizzapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var nombreUsuario by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Registro", style = MaterialTheme.typography.headlineLarge.copy(fontFamily = FontFamily.Serif),  textAlign = TextAlign.Center)
            TextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") }
            )
            TextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido") }
            )
            TextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") }
            )
            TextField(
                value = nombreUsuario,
                onValueChange = { nombreUsuario = it },
                label = { Text("Nombre de usuario") }
            )
            TextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation()
            )

            val customButtonColor = Color.Red.copy(alpha = 0.8f) // Crea un color personalizado con 81% de opacidad

            Button(
                onClick = {
                    // Aquí puedes manejar el registro del usuario
                    // Puedes acceder a las variables nombre, apellido, correo, contrasena, nombreUsuario
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = customButtonColor, // Usa el color personalizado
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .height(56.dp)
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Registrarse")
            }

            Text(
                text = "¿Ya tienes una cuenta?",
                color = Color.Black, // Cambia el color del texto según tu preferencia
                modifier = Modifier
                    .padding(top = 16.dp)

            )

            Text(
                text = "Iniciar sesión",
                color = Color.Blue, // Cambia el color del texto según tu preferencia
                modifier = Modifier
                    .padding(top = 10.dp)
                    .clickable {
                        navController.navigate("login")
                    }
            )

        }
    }
}