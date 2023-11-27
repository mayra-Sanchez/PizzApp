package com.example.pizzapp

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.pizzapp.models.CredentialsLogin
import com.example.pizzapp.models.TokenResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(navController: NavController) {
    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var isValidEmail by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var isValidPassword by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(236, 83, 76))) {
        Column(
            Modifier
                .align(Alignment.Center)
                .padding(10.dp)
                .fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(249, 238, 201)),
            ) {
                Column(
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)) {
                    ImageLogin(navController)
                    Email(
                        email = email,
                        emailChange = {
                            email = it
                            isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        },
                        isValidEmail
                    )
                    Password(
                        password = password,
                        passwordChange = {
                            password = it
                            isValidPassword = password.length >= 6

                        },
                        passwordVisible = passwordVisible,
                        passwordVisibleChange = { passwordVisible = !passwordVisible },
                        isValidPassword = isValidPassword

                    )
                    Button_login(
                        context = context,
                        isValidEmail = isValidEmail,
                        isValidPassword = isValidPassword,
                        email = email,
                        password = password,
                        navController = navController
                    )
                    lostPassword(navController = navController)
                    DontHaveAccount(navController = navController)
                }
            }
        }
    }
}

@Composable
fun ImageLogin(navController: NavController){
    Row(
        horizontalArrangement = Arrangement.Center) {
        Image(
            modifier = Modifier
                .size(400.dp)
                .clickable { navController.navigate("inicio") },
            painter = painterResource(id = R.drawable.logologin),
            contentDescription = null,
        )
    }
}

@Composable
fun DontHaveAccount(navController: NavController){
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Text(
            text = "¿No tienes una cuenta?",
            style = TextStyle(fontSize = 12.sp),
            color = Color.Black,
            modifier = Modifier
                .padding(2.dp)
        )

        Text(
            text = "Registrate",
            style = TextStyle(fontSize = 12.sp),
            color = Color.Blue,
            modifier = Modifier
                .padding(2.dp)
                .clickable {
                    navController.navigate("registro")
                }
        )
    }

}

@Composable
fun lostPassword(navController: NavController){
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Text(
            text = "¿Olvidó su contraseña?",
            style = TextStyle(fontSize = 12.sp),
            color = Color.Black,
            modifier = Modifier
                .padding(2.dp)
        )

        Text(
            text = "Reestablecer contraseña",
            style = TextStyle(fontSize = 12.sp),
            color = Color.Blue,
            modifier = Modifier
                .padding(2.dp)
                .clickable {
                    navController.navigate("forgot-password")
                }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Email(
    email:String,
    emailChange: (String)->Unit,
    isValidEmail: Boolean
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            value = email,
            onValueChange = emailChange,
            label = {Text("Email")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1,
            singleLine = true,
            colors = if(isValidEmail){
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Password(
    password: String,
    passwordChange: (String)->Unit,
    passwordVisible: Boolean,
    passwordVisibleChange: () -> Unit,
    isValidPassword: Boolean

){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            visualTransformation = if(passwordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
            shape = RoundedCornerShape(10.dp),
            value = password,
            onValueChange = passwordChange,
            maxLines = 1,
            singleLine = true,
            label  = {Text("Contraseña")},
            colors = if(isValidPassword){
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Green,
                    focusedBorderColor = Color.Green)
            }else{
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedLabelColor = Color.Red,
                    focusedBorderColor = Color.Red)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                PasswordVisibility(
                    passwordVisible = passwordVisible,
                    onPasswordVisibilityToggle = passwordVisibleChange
                )
            }
        )
    }
}

@Composable
fun PasswordVisibility(
    passwordVisible: Boolean,
    onPasswordVisibilityToggle: () -> Unit
) {
    val imageResId: Int = if (passwordVisible) {
        R.drawable.visibility_off
    } else {
        R.drawable.visibility
    }
    IconButton(
        onClick = onPasswordVisibilityToggle,
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Icon(
            painter = painterResource(id = imageResId),
            contentDescription = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
        )
    }
}
@Composable
fun Button_login(
    context: Context,
    isValidEmail: Boolean,
    isValidPassword: Boolean,
    email: String,
    password: String,
    navController: NavController
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                if (isValidEmail && isValidPassword) {
                    login(context, email, password) { token ->
                        navController.navigate("mi_perfil/${token}")
                    }
                } else {
                    Toast.makeText(context, "Verifica los campos", Toast.LENGTH_SHORT).show()
                    navController.navigate("pagina_principal")
                }
            }
        ) {
            Text(text = "Iniciar sesión")
        }
    }
}

fun login(context: Context, email: String, password: String, onSuccess:(String)-> Unit) {
    val credentials = CredentialsLogin(email, password)
    RetrofitClient.apiService.login(credentials).enqueue(object : Callback<TokenResponse> {
        override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
            if (response.isSuccessful) {
                val token = response.body()?.token ?: ""

                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()
                onSuccess(token)
                
            } else {
                Toast.makeText(context, "Error al iniciar sesión", Toast.LENGTH_LONG).show()
            }
        }

        override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
            // Aquí manejas el caso de fallo en la llamada, como una excepción o problema de conexión
            println(t.message)
            Toast.makeText(context, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
        }
    })
}




