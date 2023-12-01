package com.example.pizzapp.navbar


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pizzapp.ImageRepository
import com.example.pizzapp.R
import com.example.pizzapp.RetrofitClient
import com.example.pizzapp.decodeJWT
import com.example.pizzapp.models.TokenResponse
import com.example.pizzapp.models.User
import com.example.pizzapp.models.UserUpdate
import com.example.pizzapp.models.response
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun Navbar(navController: NavController, jwtToken: String? = null) {
    // Decodificar la información del usuario desde el token JWT
    var context = LocalContext.current;
    var isLogoutDialogOpen by remember { mutableStateOf(false) }
    var isLogoutDialogOpen2 by remember { mutableStateOf(false) }
    jwtToken?.let {
        val userData = decodeJWT(jwtToken)
        var nombreUsuario by remember {
            mutableStateOf(
                userData?.get("nombreUsuario") as? String ?: ""
            )
        }

        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(249, 238, 201))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    // Assuming ImageInitial is a custom composable
                    ImageInitial(navController)
                }

                Spacer(modifier = Modifier.weight(1.5f))
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = nombreUsuario,
                        color = Color.Black
                    ) // Cambiar el color del texto a negro
                }
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                ) {
                    IconButton(
                        onClick = {
                            expanded = !expanded
                        }
                    ) {
                        Icon(
                            Icons.Default.Settings,
                            contentDescription = "Configuración",
                            tint = Color.Black
                        ) // Cambiar el color del ícono a negro
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .padding(3.dp)
                            .background(Color(249, 238, 201))
                    )
                    {
                        DropdownMenuItem(
                            text = { Text(text = "Mi perfil") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.AccountCircle,
                                    contentDescription = "Mi perfil",
                                    tint = Color.Black
                                )
                            },
                            onClick = {
                                if (jwtToken != null) {
                                    RetrofitClient.apiService.getUser("Bearer " + jwtToken)
                                        .enqueue(object : Callback<UserUpdate> {
                                            override fun onResponse(
                                                call: Call<UserUpdate>,
                                                response: Response<UserUpdate>
                                            ) {
                                                if (response.isSuccessful) {
                                                    val userUpdate = response.body()
                                                    if (userUpdate != null) {
                                                        userUpdate.profilePicture?.let { it1 ->
                                                            userUpdate.email?.let { it2 ->
                                                                ImageRepository.saveImage(
                                                                    it2,
                                                                    it1
                                                                )
                                                            }
                                                        }
                                                        userUpdate.profilePicture=null
                                                    };
                                                    val userUpdateJson =
                                                        Gson().toJson(userUpdate)
                                                    navController.navigate("mi_perfil/$jwtToken/$userUpdateJson")

                                                } else {
                                                    val errorBody = response.errorBody()?.string()
                                                    if (errorBody != null) {
                                                        try {
                                                            val errorJson = JSONObject(errorBody)
                                                            val errorMessage = errorJson.optString(
                                                                "response",
                                                                "Ha ocurrido un error'"
                                                            )
                                                            Toast.makeText(
                                                                context,
                                                                errorMessage,
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        } catch (e: JSONException) {
                                                            e.printStackTrace()
                                                        }
                                                    }
                                                }}
                                                override fun onFailure(
                                                    call: Call<UserUpdate>,
                                                    t: Throwable
                                                ) {
                                                    // Aquí manejas el caso de fallo en la llamada, como una excepción o problema de conexión
                                                    println(t.message)
                                                    Toast.makeText(
                                                        context,
                                                        "Error de conexión: ${t.message}",
                                                        Toast.LENGTH_LONG
                                                    ).show()

                                            }
                                        })
                                }
                            })

                        DropdownMenuItem(
                            text = { Text(text = "Mis reseñas") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Create,
                                    contentDescription = "Mis reseñas",
                                    tint = Color.Black
                                )
                            },
                            onClick = { navController.navigate("mis_reseñas") })
                        DropdownMenuItem(
                            text = { Text(text = "Cerrar sesión") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.ExitToApp,
                                    contentDescription = "Cerrar sesión",
                                    tint = Color.Black
                                )
                            },
                            onClick = {
                                isLogoutDialogOpen = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text(text = "Eliminar mi cuenta") },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "Eliminar cuenta",
                                    tint = Color.Black
                                )
                            },
                            onClick = {
                                isLogoutDialogOpen2 = true
                            }
                        )
                    }
                }
            }

        }
    }
    if (isLogoutDialogOpen) {
        AlertDialog(
            onDismissRequest = { isLogoutDialogOpen = false },
            title = { Text("Cerrar sesión") },
            text = { Text("¿Está seguro que desea cerrar sesión?") },
            confirmButton = {
                Button(
                    onClick = {
                        //Acá se llamaria al servicio de cerrar sesion
                        navController.navigate("inicio")
                        isLogoutDialogOpen = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Confirmar")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        isLogoutDialogOpen = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Cancelar")
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
    if (isLogoutDialogOpen2) {
        AlertDialog(
            onDismissRequest = { isLogoutDialogOpen2 = false },
            title = { Text("Eliminar cuenta") },
            text = { Text("¿Está seguro que desea eliminar su cuenta?") },
            confirmButton = {
                Button(
                    onClick = {
                        if (jwtToken != null) {
                            RetrofitClient.apiService.desactiveUser("Bearer " + jwtToken)
                                .enqueue(object : retrofit2.Callback<User> {
                                    override fun onResponse(
                                        call: Call<User>,
                                        response: Response<User>
                                    ) {

                                        if (response.isSuccessful) {
                                            Toast.makeText(
                                                context,
                                                "Cuenta eliminada",
                                                Toast.LENGTH_LONG
                                            ).show()

                                            navController.navigate("inicio")
                                            isLogoutDialogOpen2 = false
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "No se logró hacer el proceso",
                                                Toast.LENGTH_LONG
                                            ).show()

                                        }
                                    }

                                    override fun onFailure(call: Call<User>, t: Throwable) {
                                        // Aquí manejas el caso de fallo en la llamada, como una excepción o problema de conexión
                                        Toast.makeText(
                                            context,
                                            "Error de conexión: ${t.message}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                })
                        }


                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Confirmar")

                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        isLogoutDialogOpen2 = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Cancelar")
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ImageInitial(navController: NavController) {
    Row(
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            modifier = Modifier
                .size(400.dp)
                .clickable { navController.navigate("pagina_principal") },
            painter = painterResource(id = R.drawable.image_principal),
            contentDescription = null,
        )
    }
}
