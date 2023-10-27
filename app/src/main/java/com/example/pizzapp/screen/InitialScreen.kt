package com.example.pizzapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.pizzapp.navbar.Navbar

@Composable
fun InitialScreen(navController: NavController) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(236, 83, 76))) {
        Navbar(navController)
    }
}
