package com.example.pizzapp.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pizzapp.navbar.Navbar

@Composable
fun InfoRestaurant(navController: NavController){
   Box(modifier = Modifier
       .fillMaxSize()
       .background(Color(236, 83, 76))){
    Column {
        Navbar(navController)
       Column (
           Modifier
               .fillMaxWidth()
               .weight(1f)){
           Text(text = "Karen´s pizza",
               style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 20.sp,
               color = Color(116,27,15),
               modifier = Modifier
                   .padding(10.dp)
                   .align(Alignment.CenterHorizontally))
           info()
           Spacer(modifier = Modifier.weight(1f))

       }
        buttonReview(navController = navController)
    }
   }
}

@Composable
fun info(){
        Column(modifier = Modifier
            .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
             ){
            Row(){
                Card(modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(Color(255, 255, 255))
                    .shadow(20.dp, shape = RoundedCornerShape(8.dp))
                    ){
                    Column (modifier = Modifier
                        .padding(15.dp)
                        .background(Color(255, 255, 255))){
                        Text(text = "Horario:", color = Color.Black)
                        Text(text = "__________", color = Color.Black)
                        Text(text = "Dirección:", color = Color.Black)
                        Text(text = "__________", color = Color.Black)
                        Text(text = "Numero telefonico:", color = Color.Black)
                        Text(text = "__________", color = Color.Black)
                        Text(text = "Página web:", color = Color.Black)
                        Text(text = "__________", color = Color.Black)
                    }
                }

                Card (modifier = Modifier
                    .padding(15.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .shadow(20.dp, shape = RoundedCornerShape(8.dp))
                    .background(Color(255, 255, 255))
                    ){
                    Column(modifier = Modifier
                        .padding(20.dp)
                        .background(Color(255, 255, 255))){
                        Text(text = "Tipos de pizza",
                            style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 20.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(5.dp)
                                .align(Alignment.CenterHorizontally))
                        Text(text = "Pollo con champiñones", color = Color.Black)
                        Text(text = "Piña", color = Color.Black)
                        Text(text = "Peperoni", color = Color.Black)
                    }

                }
            }
            Card(modifier = Modifier.padding(20.dp)
                .clip(RoundedCornerShape(15.dp))
                .shadow(20.dp, shape = RoundedCornerShape(8.dp)).background(Color(255,255,255))){
                Column {
                    Text(text = "Reseñas",
                        style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 20.sp,
                        color = Color(116,27,15),
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally))

                    LazyColumn(){
                        items(10){
                                index -> reviews()
                        }
                    }
                }
            }
        }
}

@Composable
fun reviews(){
    Column(){
        Box(modifier = Modifier
            .padding(10.dp)) {
            Row (modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(Color(211, 183, 134))){
                Column(modifier = Modifier.padding(10.dp)){
                    Text(
                        text = "Lali1226",
                        style = TextStyle(fontWeight = FontWeight.Bold,
                            fontSize = 16.sp, lineHeight = 24.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "5 estrellas",
                        style = TextStyle(fontWeight = FontWeight.Bold,
                            fontSize = 16.sp, lineHeight = 24.sp
                        )
                    )
                }
                Text(
                    text = "“Me gusta mucho la comida y tienen un excelente servicio”",
                    style = TextStyle(
                        fontSize = 16.sp, lineHeight = 24.sp
                    )
                )

            }
        }

    }

}
@Composable
fun buttonReview(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate("reviews") },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        ) {
            Text(
                text = "Agregar reseña",
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
                color = Color(255, 255, 255)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoRestaurantPreview() {
    val navController = rememberNavController()

    InfoRestaurant(navController)
}

