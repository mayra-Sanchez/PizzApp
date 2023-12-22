package com.example.pizzapp.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzapp.R

import com.example.pizzapp.navbar.Navbar

data class Reviews(
    var dateCreation: String? = null,
    var author: String? = null,
    var descripcion: String? = null,
    var calificacion: Int? = null,
    var email: String? = null,
    var restaurante: String? = null
)

@Composable
fun InitialScreen(navController: NavController,jwtToken:String) {
    var searchTerm by remember { mutableStateOf("") }
//    val reviews = getReviewsFromUser()

    var restaurantChange2: (String) -> Unit = { newValue ->
        println("Nuevo valor: $newValue")
    }

    var reviews by remember { mutableStateOf<List<Reviews>>(listOf()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(236, 83, 76))) {
        Column(
            Modifier
                .fillMaxWidth()){
            Navbar(navController,jwtToken )
            search(restaurant = searchTerm, restaurantChange = { searchTerm = it }, label = "Busca por lugar o por usuario")
            Row(modifier = Modifier.align(Alignment.CenterHorizontally))
            {
                Text(text = "Reseñas",
                    style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 20.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(10.dp))
                Image(
                    painter = painterResource(id = R.drawable._image4),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(5.dp)
                        .clickable { navController.navigate("crear_reseñas_pizza/$jwtToken") },
                    contentScale = ContentScale.Fit
                )

            }

            val filteredReviews = reviews.filter {
                (it.restaurante?.contains(searchTerm, ignoreCase = true) ?: false) ||
                (it.author?.contains(searchTerm, ignoreCase = true) ?: false)
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f) // Esto asigna un peso de 1 a LazyColumn
            ) {
                items(filteredReviews){ reviews ->
                    reviewsUsers(reviews = reviews)
                }
            }
        }
    }
}


suspend fun getReviewsFromBackend(token: String): List<Reviews> {
    // Implementa la lógica para obtener las reseñas desde tu backend aquí
    return listOf() // Devuelve las reseñas
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun search(
    restaurant:String,
    restaurantChange: (String)->Unit,
    label: String
){
    Row(modifier = Modifier
        .padding(20.dp)
        .clip(RoundedCornerShape(15.dp))
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable._image2),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp).padding(5.dp),
            contentScale = ContentScale.Fit
        )
        TextField(
            value = restaurant,
            onValueChange = restaurantChange,
            label = { Text(label) },
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .shadow(20.dp, shape = RoundedCornerShape(8.dp))
                .background(Color.White)
            ,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {

                }
            ),
        )

    }


}

@Composable
fun reviewsUsers(reviews: Reviews){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .shadow(20.dp, shape = RoundedCornerShape(8.dp))
            .background(Color.White),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Column(modifier = Modifier.padding(8.dp)){
                    Text(text = "Usuario: ", fontWeight = FontWeight.Bold, color = Color.Black, )
                    Text(text = "${reviews.author}", color = Color.Black)
                }
                Text(
                    text = "\"${reviews.descripcion}\"",
//                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Add spacing between columns

            Column(modifier = Modifier.weight(1f)) {

//                Column(modifier = Modifier.padding(4.dp)){
//                    Text(text = "Tipo de pizza: ", fontWeight = FontWeight.Bold, color = Color.Black, )
//                    Text(text = "${reviews.pizzaType}", color = Color.Black)
//                }

                Column(modifier = Modifier.padding(4.dp)){
                    Text(text = "Lugar: ", fontWeight = FontWeight.Bold, color = Color.Black, )
                    Text(text = "${reviews.restaurante}", color = Color.Black)
                }
                Column(modifier = Modifier.padding(4.dp)){
                    Text(text = "Cantidad de estrellas: ", fontWeight = FontWeight.Bold, color = Color.Black, )
                    Text(text = "${reviews.calificacion}", color = Color.Black)
                }
            }
        }
    }
}

fun getReviewsFromUser(): List<Reviews> {
    return listOf(
        Reviews("Gomez12","Karen´s pizza", "Me gustó mucho, muy rica la comida y la pizza estaba muy rica", 5),
        Reviews("Gomez1226","Dominos", "Me gustó mucho, muy rica la comida y la pizza estaba muy rica",5),
        Reviews("Lali","Papa jhons", "Me gustó mucho, muy rica la comida y la pizza estaba muy rica", 5),
        Reviews("Juan12","Pizza anita", "Me gustó mucho, muy rica la comida y la pizza estaba muy rica", 5),
        Reviews("Marce1","Karen´s pizza", "Me gustó mucho, muy rica la comida y la pizza estaba muy rica",5),
    )
}




