package com.example.pizzapp.Screen

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzapp.R
import com.example.pizzapp.RetrofitClient
import com.example.pizzapp.models.TokenResponse
import com.example.pizzapp.models.resetPassword
import com.example.pizzapp.models.response
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

@Composable
fun ForgotPassword(navController: NavController){
    val context = LocalContext.current

    var emailP by remember { mutableStateOf("") }
    var isValidEmailP by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(236, 83, 76))) {
        Column(
            Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(249, 238, 201)),
            ) {
                Column(
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)) {
                    Image()
                    EmailVerification(emailP = emailP, emailChange = {
                        emailP = it
                        isValidEmailP = Patterns.EMAIL_ADDRESS.matcher(emailP).matches()
                    }, isValidEmailP)
                    sendCode(navController = navController, emailP = emailP, isValidEmailP = isValidEmailP, context = context)
                }
            }
        }
    }
}

@Composable
fun Image(){
    Row(
        horizontalArrangement = Arrangement.Center) {
        Image(
            modifier = Modifier.size(400.dp),
            painter = painterResource(id = R.drawable.logologin),
            contentDescription = null,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailVerification(
    emailP:String,
    emailChange: (String)->Unit,
    isValidEmailP: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            value = emailP,
            onValueChange = emailChange,
            label = {Text("Ingrese su email")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1,
            singleLine = true,
            colors = if(isValidEmailP){
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Green,
                    focusedBorderColor = Color.Green)
            }else{
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Red,
                    focusedBorderColor = Color.Red)
            },
        )
    }
}

@Composable
fun sendCode(context: Context,
             isValidEmailP: Boolean,
             emailP: String,
             navController: NavController){
    Row(horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth(),
    ){
        androidx.compose.material3.Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                val resetPassword= resetPassword(emailP)
                RetrofitClient.apiService.sendEmailPassword(resetPassword).enqueue(object: retrofit2.Callback<response> {
                    override fun onResponse(call: Call<response>, response: Response<response>) { if (response.isSuccessful) {
                            val responseBody = response.body()?.response
                            if (responseBody != null) {
                                Toast.makeText(context, responseBody, Toast.LENGTH_SHORT).show()
                                navController.navigate("reset-password/$emailP")

                            }
                        }else{
                            val errorBody = response.errorBody()?.string()
                            if (errorBody != null) {
                                try {
                                    val errorJson = JSONObject(errorBody)
                                    val errorMessage = errorJson.optString("response", "Ha ocurrido un error")

                                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                } catch (e: JSONException) {
                                    e.printStackTrace()
                                }
                            }
                        }


                    }

                    override fun onFailure(call: Call<response>, t: Throwable) {
                        Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
            }


        ) {
            Text(text = "Reestablecer contraseña")
        }

    }
}
