package com.example.pizzapp.reviews

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzapp.R
import com.example.pizzapp.navbar.Navbar


@Composable
fun Reviews(navController: NavController){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(236, 83, 76))){
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.Transparent)){
            Navbar(navController)
            Text(text = "Karen´s pizza",
                style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 20.sp,
                color = Color(116,27,15),
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally))
            Box(modifier = Modifier.fillMaxWidth()
                .fillMaxHeight()){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(text = "Elige un tipo de reseña:",
                        style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 20.sp,
                        color = Color(116,27,15),
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.Start))
                    Button(onClick = { navController.navigate("agregar_reseña_restaurante") },
                        modifier = Modifier
                            .padding(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center){
                            Image(
                                painter = painterResource(id = R.drawable.pizzapp__9_),
                                contentDescription = null,
                                modifier = Modifier.size(35.dp))
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(text = "Reseña del restaurante")
                        }
                    }
                        Button(onClick = { navController.navigate("agregar_reseña_comida") },
                            modifier = Modifier
                            .padding(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center){
                                Image(
                                    painter = painterResource(id = R.drawable.pizzapp__8_),
                                    contentDescription = null,
                                    modifier = Modifier.size(35.dp),

                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(text = "Reseña de alguna pizza")
                            }
                        }
                }
            }
        }
    }
}


