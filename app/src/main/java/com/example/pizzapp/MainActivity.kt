package com.example.pizzapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pizzapp.ui.theme.PizzAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            PizzAppTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color =Color(0xFFF9EEC9)
                ) {

                    NavHost(
                        navController = navController,
                        startDestination = "inicio"
                    ) {
                        composable("inicio") {
                            inicio(navController = navController)
                        }
                        composable("registro") {
                            RegisterScreen(navController = navController)
                        }
                        // Agrega más composable y rutas según sea necesario
                    }
                }
            }
        }
    }
}

@Composable
fun inicio(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.pizzapp),
            contentDescription = stringResource(id = R.string.Logo_inicial),
            modifier = Modifier
                .size(300.dp)
                .padding(bottom = 10.dp)
        )


        // Botones de inicio y registro
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { /* Acción al hacer clic en "Inicio" */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
            ) {
                Text(text = "Inicio", color = Color.White)
            }
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = { navController.navigate("registro")},
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier
                    .weight(1f)
                    .height(60.dp)
            ) {
                Text(text = "Registro", color = Color.White)
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun AppPreview() {
    PizzAppTheme {

    }
}