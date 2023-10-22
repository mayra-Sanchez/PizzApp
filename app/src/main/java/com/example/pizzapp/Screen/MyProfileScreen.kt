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
import com.example.pizzapp.FirestoreRepository
import com.example.pizzapp.User
import com.example.pizzapp.navbar.Navbar

@Composable
fun MyProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val firestoreRepository = FirestoreRepository()

    // Obtener el usuario actual
    val user = firestoreRepository.getCurrentUser()

    // Variables para almacenar los datos del usuario
    var email by remember { mutableStateOf("") }
    var isValidEmail by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    var isValidPassword by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var nombreUsuario by remember { mutableStateOf("") }

    // Si hay un usuario autenticado
    user?.let {
        // Obtener el UID del usuario actual
        val userId = it.uid

        // Obtener el documento de usuario desde Firestore
        val userDocument = firestoreRepository.getUserDocument(userId)

        // Obtener los datos del documento
        userDocument.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                val user = documentSnapshot.toObject(User::class.java)
                // Asignar los datos del usuario a las variables
                nombre = user?.nombre ?: ""
                apellido = user?.apellido ?: ""
                nombreUsuario = user?.nombreUsuario ?: ""
                email = user?.correo ?: ""
                password = user?.password ?: ""
            } else {
                // El documento no existe
            }
        }.addOnFailureListener { e ->
            // Ocurrió un error al obtener el documento
        }
    }

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
            ActualizarTexto("Contraseña", password, onValueChange = { password = it })

            ButtonUpdate(context = context,
                isValidEmail = isValidEmail,
                isValidPassword = isValidPassword,
                email = email,
                password = password,
                nombre = nombre,
                apellido = apellido,
                nombreUsuario = nombreUsuario,
                navController = navController,
                userId = user?.uid // Pasa el UID del usuario actual
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
    password: String,
    nombre: String,
    apellido: String,
    nombreUsuario: String,
    navController: NavController,
    userId: String? // Agrega este parámetro
){
    val firestoreRepository = FirestoreRepository()

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
                        "nombreUsuario" to nombreUsuario,
                        "password" to password
                    )
                    firestoreRepository.updateUser(userId ?: "", updatedData,
                        onSuccess = {
                            Toast.makeText(context, "Datos actualizados con éxito", Toast.LENGTH_LONG).show()
                            navController.navigate("mi_perfil")
                        },
                        onFailure = { e ->
                            Toast.makeText(context, "Error al actualizar: ${e.message}", Toast.LENGTH_LONG).show()
                        })
                } else {
                    Toast.makeText(context, "Revisa los campos", Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Text(text = "Actualizar")
        }

    }
}
