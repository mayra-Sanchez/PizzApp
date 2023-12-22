package com.example.pizzapp.Screen

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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzapp.navbar.Navbar

data class Review(
    val reviewRestaurant: String,
    val placeName: String,
    val placeAddress: String,
    val starRating: Int,
    val pizzaType: String
)
@Composable
fun MyReview(navController: NavController, jwtToken:String) {
    val reviews = getReviewsForUser()

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
            Navbar(navController, jwtToken)
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
                    .weight(1f) // Esto asigna un peso de 1 a LazyColumn
            ) {
                items(reviews){ review ->
                    ReviewItem(review = review)
                }
            }
        }
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Card(
        modifier = Modifier
            .padding(16.dp).shadow(20.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.White),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
//
                Text(text = "Lugar: ", fontWeight = FontWeight.Bold, color = Color.Black, )
                Text(text = "${review.placeName}", color = Color.Black, modifier = Modifier.padding(4.dp))

                Text(
                    text = "\"${review.reviewRestaurant}\"",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black // Set text color to black
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Add spacing between columns

            Column(modifier = Modifier.weight(1f)) {

                Column(modifier = Modifier.padding(4.dp)){
                    Text(text = "Tipo de pizza: ", fontWeight = FontWeight.Bold, color = Color.Black, )
                    Text(text = "${review.pizzaType}", color = Color.Black)
                }
                Column(modifier = Modifier.padding(4.dp)){
                    Text(text = "Cantidad de estrellas: ", fontWeight = FontWeight.Bold, color = Color.Black, )
                    Text(text = "${review.starRating}", color = Color.Black)
                }
            }
        }
    }
}

fun getReviewsForUser(): List<Review> {
    return listOf(
        Review("Buena comida, buen servicio.","Pizzería Uno", "123 Main St", 4, "Pepperoni"),
        Review("Excelente pizza, me encanta.","Pizza Hut", "456 Elm St", 5, "Margherita"),
        Review("Siempre entregan a tiempo.","Domino's", "789 Oak St", 3, "Supreme"),
        Review("Buena comida, buen servicio.","Little Caesars", "321 Elm St", 4, "Cheese"),
        Review("La entrega fue un poco lenta.","Papa John's", "555 Oak St", 5, "Hawaiian"),
        Review("Excelente pizza, me encanta.","Pizza Express", "987 Pine St", 4, "Vegetarian"),
        Review("La carne estaba un poco dura.","Pizza Palace", "654 Maple St", 3, "Meat Lovers"),
        Review("La mejor pizza de pepperoni en la ciudad.","Pizza World", "111 Cedar St", 5, "Pepperoni"),
        Review("Excelente pizza, me encanta.","Pizza Heaven", "222 Birch St", 4, "Buffalo Chicken"),
        Review("La entrega fue un poco lenta.","Pizza Delight", "333 Spruce St", 3, "Veggie Supreme")
    )
}