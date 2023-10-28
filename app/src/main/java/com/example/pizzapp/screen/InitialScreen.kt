package com.example.pizzapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.pizzapp.navbar.Navbar

@Composable
fun Initial(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(236, 83, 76))) {
        Navbar(navController)
        /*
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate("mis_reseñas_lugares")}) {
            Text(text = "Reseñas de lugares")
        }
        */

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate("crear_reseñas_lugar")}) {
            Text(text = "Crear reseñas de lugares")
        }
    /*
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier.fillMaxWidth(),
            onClick = { navController.navigate("crear_reseñas_pizza")}) {
            Text(text = "Crear reseñas de pizzas")
        }
        */
    }
}
