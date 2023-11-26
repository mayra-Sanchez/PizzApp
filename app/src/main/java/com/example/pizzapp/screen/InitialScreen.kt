package com.example.pizzapp.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.TextField
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

@Composable
fun InitialScreen(navController: NavController) {

    var restaurantChange2: (String) -> Unit = { newValue ->
        println("Nuevo valor: $newValue")
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(236, 83, 76))) {
        Column(
            Modifier
                .fillMaxWidth()){
            Navbar(navController)
            search(restaurant = " ", restaurantChange = restaurantChange2)
            Text(text = "RESTAURANTES",
                style = TextStyle(fontWeight = FontWeight.Bold), fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally))
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(10){
                        index -> restaurants(navController = navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun search(
    restaurant:String,
    restaurantChange: (String)->Unit,
){
    Row(modifier = Modifier
        .padding(20.dp)
        .clip(RoundedCornerShape(15.dp))
        .background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = R.drawable.pizzapp__7_),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .padding(8.dp),
            contentScale = ContentScale.Fit
        )
        TextField(
            value = restaurant,
            onValueChange = restaurantChange,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .shadow(10.dp, shape = RoundedCornerShape(10.dp))
                .background(Color.Transparent)
                .fillMaxWidth()
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
fun restaurants(navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .shadow(20.dp, shape = RoundedCornerShape(8.dp))
            .background(Color(255, 255, 255))
    ) {
        Column {
            Text(
                text = "Karen's pizza",
                color = Color(116, 27, 15),
                style = TextStyle(fontWeight = (FontWeight.Bold)),
                fontSize = 25.sp,
                modifier = Modifier.padding(16.dp).clickable {
                    navController.navigate("info-restaurant")
                }
            )
            Box(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                Row {
                    Text(
                        text = "Somos una cadena de restaurantes de comida italiana que fusiona diferentes ingredientes de la gastronomía",
                        style = TextStyle(
                            fontSize = 16.sp, lineHeight = 24.sp
                        ),
                        modifier = Modifier.weight(2f).padding(10.dp)
                    )

                    Image(
                        painter = painterResource(id = R.drawable.logok_12a17e4a),
                        contentDescription = null,
                        modifier = Modifier.weight(1f)
                            .fillMaxWidth(),
                        contentScale = ContentScale.Fit                )
                }
            }


        }

    }

}