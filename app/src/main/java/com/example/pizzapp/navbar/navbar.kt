package com.example.pizzapp.navbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzapp.FirestoreRepository

import com.example.pizzapp.R
import com.example.pizzapp.models.User


@Composable
fun Navbar(navController: NavController) {
    val firestoreRepository = FirestoreRepository()

    // Obtener el usuario actual
    val user = firestoreRepository.getCurrentUser()

    // Variable para almacenar el nombre de usuario
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
                // Asignar los datos del usuario a la variable
                nombreUsuario = user?.nombreUsuario ?: ""
            } else {
                // El documento no existe
            }
        }.addOnFailureListener { e ->
            // Ocurrió un error al obtener el documento
        }
    }

    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color(249, 238, 201))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                // Assuming ImageInitial is a custom composable
                ImageInitial(navController)
            }
            Spacer(modifier = Modifier.weight(1.5f))
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = nombreUsuario, color = Color.Black) // Cambiar el color del texto a negro
            }
            Box(
                modifier = Modifier
                    .weight(0.5f)
            ) {
                IconButton(
                    onClick = {
                        expanded = !expanded
                    }
                ) {
                    Icon(Icons.Default.Settings, contentDescription = "Configuración", tint = Color.Black) // Cambiar el color del ícono a negro
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .padding(3.dp)
                        .background(Color(249, 238, 201))
                ) {
                    DropdownMenuItem(text = { Text(text = "Mi perfil") }, leadingIcon = {Icon(Icons.Default.AccountCircle, contentDescription = "Mi perfil", tint = Color.Black)} , onClick = { navController.navigate("mi_perfil") } )
                    DropdownMenuItem(text = { Text(text = "Mis reseñas")}, leadingIcon = {Icon(Icons.Default.Create, contentDescription = "Mis reseñas", tint = Color.Black)} , onClick = { navController.navigate("mis_reseñas") })
                    DropdownMenuItem(text = { Text(text = "Cerrar sesión")}, leadingIcon = {Icon(Icons.Default.Close, contentDescription = "Cerrar sesión", tint = Color.Black)}, onClick = { navController.navigate("inicio") })
                }
            }
        }
    }
}


@Composable
fun ImageInitial(navController: NavController){
    Row(
        horizontalArrangement = Arrangement.Start) {
        Image(
            modifier = Modifier
                .size(400.dp)
                .clickable { navController.navigate("pagina_principal") },
            painter = painterResource(id = R.drawable.image_principal),
            contentDescription = null,
        )
    }
}