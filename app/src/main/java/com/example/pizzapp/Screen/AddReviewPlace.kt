package com.example.pizzapp.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzapp.Pizzerias
import com.example.pizzapp.navbar.Navbar

@Composable
fun AddReviewPlace(navController: NavController, pizzeria: Pizzerias) {
    var reviewText by remember { mutableStateOf("") }
    var rating by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(236, 83, 76))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Navbar(navController)
                Text(
                    text = pizzeria.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF741B0F),
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
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = "Ingresa tu reseña:",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .align(Alignment.Start)
                                        .padding(8.dp)
                                )
                                BasicTextField(
                                    value = reviewText,
                                    onValueChange = { reviewText = it },
                                    textStyle = TextStyle.Default.copy(lineHeight = 20.sp), // Ajusta la altura de línea
                                    singleLine = false,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .heightIn(min = 100.dp)
                                        .border(
                                            1.dp,
                                            Color.Black,
                                            RoundedCornerShape(8.dp)
                                        ) // Añade un borde
                                        .padding(8.dp)
                                        .background(Color.White)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Selecciona tu calificación:",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .align(Alignment.Start)
                                        .padding(8.dp)
                                )
                                RatingBar(
                                    rating = rating,
                                    onRatingChanged = { newRating -> rating = newRating }
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = {
                                        // Lógica para procesar la reseña y la calificación
                                        ButtonReviewPlace(reviewText, rating)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .size(width = 50.dp, height = 40.dp) // Establece el tamaño
                                        .shadow(1.dp, shape = RoundedCornerShape(8.dp)),
                                    colors = ButtonDefaults.buttonColors(Color(254,2, 23))
                                ) {
                                    Text(
                                        text = "Enviar Reseña",
                                        color = Color.White, // Establece el color del texto
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}

fun ButtonReviewPlace(review: String, rating: Int) {
    // Aquí puedes hacer lo que necesites con la reseña y la calificación
    // Por ejemplo, guardarlos en una base de datos o enviarlos a un servidor
}

@Composable
fun RatingBar(
    rating: Int,
    onRatingChanged: (Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(7.dp)

    ) {
        (1..5).forEach { i ->
            Icon(
                imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.Add,
                contentDescription = null,
                tint = Color(0xFF741B0F), // Color del ícono
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onRatingChanged(i) }
            )
        }
    }
}