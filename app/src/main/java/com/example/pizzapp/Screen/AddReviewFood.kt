package com.example.pizzapp.Screen

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzapp.RetrofitClient
import com.example.pizzapp.decodeJWT
import com.example.pizzapp.models.Pizzerias
import com.example.pizzapp.models.TokenResponse
import com.example.pizzapp.models.Review
import com.example.pizzapp.navbar.Navbar
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import org.threeten.bp.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewFood(navController: NavController, pizzeria: Pizzerias, jwtToken:String) {
    var reviewText by remember { mutableStateOf("") }
    var nameRestaurant by remember { mutableStateOf("") }
    var pizzaName by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf("") }
    var rating by remember { mutableIntStateOf(0) }
    val context = LocalContext.current
    val userData = decodeJWT(jwtToken)
    var nombreUsuario by remember {
        mutableStateOf(
            userData?.get("nombreUsuario") as? String ?: ""
        )
    }
    var email by remember {
        mutableStateOf(
            userData?.get("sub") as? String ?: ""
        )
    }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            calendar.set(year, month, dayOfMonth)
            selectedDate = format.format(calendar.time)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(236, 83, 76))){
        Column(
        ) {
            Navbar(navController, jwtToken)
            Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally){
                showUser(value = nombreUsuario, onChange = {nombreUsuario = it}, label = "Usuario")


                text(value = nameRestaurant, onChange = {
                    nameRestaurant = it}, label = "Escriba el restaurante")

                reviewText(value = reviewText, onChange = {
                    reviewText = it}, label = "Escriba su reseña")

                //FECHA-----------------------------------------------------------------
                TextField(
                    value = selectedDate,
                    onValueChange = {},
                    label = { Text("Fecha de la reseña") },
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    singleLine = true,
                    maxLines = 1,
                    shape = RoundedCornerShape(10.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
                Button(onClick = { datePickerDialog.show() }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                    Text("Seleccionar Fecha")
                }
                //------------------------------------------------------------------------

            }
            Text(
                text = "Selecciona tu calificación:",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center, // Centra los elementos horizontalmente
                modifier = Modifier.fillMaxWidth()
            ) {
                RatingBarReview(
                    rating = rating,
                    onRatingChanged = { newRating -> rating = newRating }
                )
            }
            addReview(
                context = context,
                textReview = reviewText,
                restaurantsName = nameRestaurant,
                dateReview = selectedDate,
                email = email,
                userName = nombreUsuario,
                calification = rating,
                jwtToken= jwtToken
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showUser(value: String, onChange: (String) -> Unit, label: String){
    TextField(
        value = value,
        onValueChange = onChange,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        label = { Text(label) },
        readOnly = true,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun text(value: String, onChange: (String) -> Unit, label: String){
    TextField(
        value = value,
        onValueChange = onChange,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        label = { Text(label) },
        singleLine = true,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun reviewText(value: String, onChange: (String) -> Unit, label: String){
    TextField(
        value = value,
        onValueChange = onChange,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        label = { Text(label) },
        singleLine = false,
        maxLines = 5,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .heightIn(min = 150.dp)
    )
}

@Composable
fun addReview(context: Context, textReview: String, restaurantsName:String, dateReview: String, email: String, userName: String, calification: Int, jwtToken:String){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                val dateTime = Instant.parse(dateReview)
                val newReview = Review(dateTime, userName, textReview, calification, email, restaurantsName)
                RetrofitClient.apiService.crearReseña("Bearer "+jwtToken, newReview).enqueue(object: retrofit2.Callback<TokenResponse> {
                    override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Reseña creada con exito", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(context, "Error en la creación de la reseña: ${response.message()}", Toast.LENGTH_LONG).show()
                        }
                    }
                    override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                        Toast.makeText(context, "Error en la comunicación: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
            },
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

@Composable
fun RatingBarReview(
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

// Lista de tipos de pizza disponibles
val pizzaTypes = listOf("Margherita", "Pepperoni", "Vegetarian", "Hawaiian", "Supreme")