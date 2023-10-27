package com.example.pizzapp.screen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
    val isValidEmail by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }
    val isValidPassword by remember { mutableStateOf(false) }
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

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(255, 204, 51, 255))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Navbar(navController)
                Text(
                    text = "Mi perfil",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // Esto asigna un peso de 1 a LazyColumn
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color(255, 204, 51, 255))
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Nombre",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Start).padding(8.dp)
                                )
                                ActualizarTexto(nombre, onValueChange = { nombre = it })
                                Text(
                                    text = "Apellido",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Start).padding(8.dp)
                                )
                                ActualizarTexto(apellido, onValueChange = { apellido = it })
                                Text(
                                    text = "Nombre de usuario",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Start).padding(8.dp)
                                )
                                ActualizarTexto(
                                    nombreUsuario,
                                    onValueChange = { nombreUsuario = it })
                                Text(
                                    text = "Email",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Start).padding(8.dp)
                                )
                                ActualizarTexto(email, onValueChange = { email = it })
                                Text(
                                    text = "Contraseña",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.align(Alignment.Start).padding(8.dp)
                                )
                                ActualizarTexto(password, onValueChange = { password = it })
                            }
                        }
                    }
                }

                ButtonUpdate(context = context,
                    isValidEmail = isValidEmail,
                    isValidPassword = isValidPassword,
                    email = email,
                    password = password,
                    nombre = nombre,
                    apellido = apellido,
                    nombreUsuario = nombreUsuario,
                    navController = navController,
                    userId = user?.uid
                )
            }
        }
    }
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
            } ,
            colors = ButtonDefaults.buttonColors(Color(249,238, 201))
        ) {
            Text(text = "Actualizar")
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActualizarTexto(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White) // Añade este modificador
    )
}