package com.example.pizzapp.Screen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzapp.R
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
               .background(Color.Transparent)){

           Text(text = "Karen´s pizza",
               style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 20.sp,
               color = Color(116,27,15),
               modifier = Modifier
                   .padding(10.dp)
                   .align(Alignment.CenterHorizontally))
           info()
           Spacer(modifier = Modifier.height(16.dp))
       }
        buttonReview(navController = navController)
    }
   }
}

@Composable
fun info(){
        Row(modifier = Modifier
            .padding(20.dp)
            .background(Color.Transparent),
            verticalAlignment = Alignment.CenterVertically
             ){
            Column(modifier = Modifier.background(Color.Transparent)){
                Card (modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .shadow(20.dp, shape = RoundedCornerShape(8.dp))
                    .background(Color(255, 255, 255))
                    .padding(20.dp)){
                    Text(text = "Horario:", color = Color.Black)
                    Text(text = "__________", color = Color.Black)
                    Text(text = "Dirección:", color = Color.Black)
                    Text(text = "__________", color = Color.Black)
                    Text(text = "Numero telefonico:", color = Color.Black)
                    Text(text = "__________", color = Color.Black)
                    Text(text = "Página web:", color = Color.Black)
                    Text(text = "__________", color = Color.Black)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Card (modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .shadow(20.dp, shape = RoundedCornerShape(8.dp))
                    .background(Color(255, 255, 255))
                    .padding(20.dp)){
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
            Column(modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .padding(20.dp)
                .shadow(20.dp, shape = RoundedCornerShape(8.dp))
                .background(Color.White)
                .weight(1f)
                .fillMaxWidth(),
                ){
                    Text(text = "Reseñas",
                        style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 20.sp,
                        color = Color(116,27,15),
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterHorizontally))


            }

        }

}

@Composable
fun reviews(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .shadow(20.dp, shape = RoundedCornerShape(8.dp))
            .background(Color(255, 255, 255))
    ) {
        Column(modifier = Modifier.background(Color.Transparent)){
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.Transparent)) {
                Row (modifier = Modifier.background(Color(136,104,57))){
                    Column {
                        Text(
                            text = "Nombre de usuario",
                            style = TextStyle(
                                fontSize = 16.sp, lineHeight = 24.sp
                            ),
                            modifier = Modifier
                                .weight(2f)
                                .padding(10.dp)
                        )
                        Text(
                            text = "Estrellas",
                            style = TextStyle(
                                fontSize = 16.sp, lineHeight = 24.sp
                            ),
                            modifier = Modifier
                                .weight(2f)
                                .padding(10.dp)
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.logok_12a17e4a),
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Fit)
                }
            }


        }

    }
}
@Composable
fun buttonReview(navController: NavController){
    Spacer(modifier = Modifier.height(16.dp))
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center){
        Button(onClick = {navController.navigate("reviews") },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        ) {
        Text(text = "Agregar reseña",
            style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 20.sp,
            color = Color(255,255,255))

    } }
}

