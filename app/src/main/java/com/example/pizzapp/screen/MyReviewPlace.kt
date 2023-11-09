package com.example.pizzapp.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzapp.navbar.Navbar

@Composable
fun MyReviewPlaceScreen(navController: NavController, pizzeria: Pizzeria) {
    val reviews = getReviewsForPlaceAndUser(pizzeria)

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
                    text = pizzeria.name, // Mostrar el nombre de la pizzería
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF741B0F), // HEX #741B0F
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    text = "Mis reseñas",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally) // Centrar verticalmente
                )
                Text(
                    text = "Total de reseñas: ${reviews.size}",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(12.dp)
                        .align(Alignment.Start)
                )

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    items(reviews){ review ->
                        MyReviewItemPlace(review = review)
                    }
                }
            }
        }
    }
}

data class Pizzeria(
    val name: String,
    val address: String
)

val pizzeria = Pizzeria(name = "Pizzería Delicioso", address = "Calle Principal 123")

// Ejemplo de reseñas del mismo usuario para la pizzería
fun getReviewsForPlaceAndUser(pizzeria: Pizzeria): List<Review> {
    // Simulación de reseñas del usuario en una pizzeria específica
    return listOf(
        Review(
            reviewRestaurant = "Gran lugar para pizza!",
            pizzaType = "Margherita",
            placeName = pizzeria.name,
            placeAddress = pizzeria.address,
            starRating = 5
        ),
        Review(
            reviewRestaurant = "Buen ambiente y servicio.",
            pizzaType = "Pepperoni",
            placeName = pizzeria.name,
            placeAddress = pizzeria.address,
            starRating = 4
        ),
        Review(
            reviewRestaurant = "Probé la pizza de hongos, ¡deliciosa!",
            pizzaType = "Hongo",
            placeName = pizzeria.name,
            placeAddress = pizzeria.address,
            starRating = 4
        )
    )
}


@Composable
fun MyReviewItemPlace(review: Review) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.White),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "\"${review.reviewRestaurant}\"",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(text = "Tipo de pizza: ${review.pizzaType}", color = Color.Black)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Lugar: ${review.placeName}", color = Color.Black)
                Text(text = "Dirección: ${review.placeAddress}", color = Color.Black)
                Text(text = "Cantidad de estrellas: ${review.starRating}", color = Color.Black)
            }
        }
    }
}