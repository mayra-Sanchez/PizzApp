package com.example.pizzapp

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.text.input.KeyboardType
import com.example.pizzapp.models.User

@Composable
fun RegisterScreen(navController: NavController) {
    var isChecked by remember { mutableStateOf(false) }
    var isDialogOpen by remember { mutableStateOf(false) }

    val context = LocalContext.current

    var email by remember { mutableStateOf("") }
    var isValidEmail by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var isValidPassword by remember { mutableStateOf(false) }

    var passwordVisible by remember { mutableStateOf(false) }

    var nombre by remember { mutableStateOf("") }
    var apellido by remember { mutableStateOf("") }
    var nombreUsuario by remember { mutableStateOf("") }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(236, 83, 76))) {
        Column(
            Modifier
                .align(Alignment.Center)
                .padding(11.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(249, 238, 201)),
            ) {
                Column(
                    Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                ) {
                    ImageRegister(navController)
                    Nombre(nombre = nombre, nombreChange = {
                        nombre = it})
                    Apellido(apellido = apellido, apellidoChange = {
                        apellido = it})
                    Email(
                        email = email,
                        emailChange = {
                            email = it
                            isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                        },
                        isValidEmail
                    )
                    NombreUsuario(
                        nombreUsuario = nombreUsuario,
                        nombreUsuarioChange ={
                            nombreUsuario = it}
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

                    TermsAndConditions(
                        isChecked = isChecked,
                        onCheckedChange = { newCheckedState ->
                            isChecked = newCheckedState
                        },
                        showDialog = { isDialogOpen = true } // Esta función abre el diálogo
                    )

                    TermsAndConditionsDialog(
                        isOpen = isDialogOpen,
                        onClose = { isDialogOpen = false }, // Esta función cierra el diálogo
                        content = {
                            // Aquí coloca el contenido de tus términos y condiciones
                            Text("Términos y Condiciones de Uso - PizzApp\n" +
                                    "\n" +
                                    "Última actualización: 25/11/23\n" +
                                    "\n" +
                                    "Bienvenido a PizzApp, una aplicación para crear y compartir reseñas de pizzas. El acceso y uso de esta aplicación están sujetos a los siguientes términos y condiciones. Al utilizar PizzApp, aceptas estos términos en su totalidad.\n" +
                                    "\n" +
                                    "1. Registro de Usuarios\n" +
                                    "\n" +
                                    "1.1 Al registrarte en PizzApp, proporcionas información veraz, precisa y actualizada, incluyendo tu nombre, apellido, correo electrónico y una foto de perfil. Te comprometes a mantener actualizada toda la información proporcionada durante el registro.\n" +
                                    "\n" +
                                    "1.2 Te responsabilizas de mantener la confidencialidad de tu contraseña y de cualquier actividad que ocurra en tu cuenta.\n" +
                                    "\n" +
                                    "2. Privacidad\n" +
                                    "\n" +
                                    "2.1 Al utilizar PizzApp, aceptas nuestra Política de Privacidad, que describe cómo recopilamos, utilizamos y protegemos tu información personal. Al otorgarnos acceso a tu galería y cámara, aceptas el uso de estas funcionalidades para cargar fotos de perfil y contenido relacionado con las reseñas de pizzas.\n" +
                                    "\n" +
                                    "3. Uso de la Aplicación\n" +
                                    "\n" +
                                    "3.1 PizzApp es una plataforma para compartir reseñas de pizzas y explorar las opiniones de otros usuarios. Al crear reseñas, te comprometes a ofrecer opiniones honestas y precisas sobre las experiencias con las pizzas y las pizzerías mencionadas.\n" +
                                    "\n" +
                                    "3.2 Eres responsable del contenido que publicas en PizzApp. No debes enviar, publicar o compartir contenido que sea difamatorio, obsceno, ilegal o que infrinja los derechos de terceros.\n" +
                                    "\n" +
                                    "4. Propiedad Intelectual\n" +
                                    "\n" +
                                    "4.1 Todo el contenido proporcionado por PizzApp, incluyendo texto, gráficos, logotipos, imágenes, videos, y software, está protegido por derechos de autor y otras leyes de propiedad intelectual.\n" +
                                    "\n" +
                                    "4.2 Al utilizar PizzApp, no adquieres ningún derecho sobre la propiedad intelectual de la aplicación o su contenido. No puedes reproducir, distribuir, modificar o crear obras derivadas basadas en dicho contenido sin autorización previa.\n" +
                                    "\n" +
                                    "5. Modificaciones y Terminación\n" +
                                    "\n" +
                                    "5.1 Nos reservamos el derecho de modificar, suspender o interrumpir PizzApp en cualquier momento y por cualquier motivo sin previo aviso.\n" +
                                    "\n" +
                                    "5.2 Si incumples estos términos y condiciones, nos reservamos el derecho de terminar tu acceso a PizzApp sin previo aviso.\n" +
                                    "\n" +
                                    "6. Limitación de Responsabilidad\n" +
                                    "\n" +
                                    "6.1 PizzApp se proporciona \"tal cual\" y no ofrecemos garantías de ningún tipo con respecto a su precisión, confiabilidad o disponibilidad.\n" +
                                    "\n" +
                                    "6.2 No seremos responsables de ningún daño directo, indirecto, incidental, especial, consecuente o punitivo que surja del uso de PizzApp o la imposibilidad de usarlo.\n" +
                                    "\n" +
                                    "7. Ley Aplicable\n" +
                                    "\n" +
                                    "7.1 Estos términos y condiciones se rigen por las leyes del [país/estado] sin tener en cuenta sus conflictos de principios legales.\n" +
                                    "\n" +
                                    "Al utilizar PizzApp, aceptas estos términos y condiciones. Si no estás de acuerdo con alguno de estos términos, por favor, no utilices esta aplicación.\n" +
                                    "\n" +
                                    "Para preguntas o comentarios sobre estos términos y condiciones, contáctanos en PizzApp1226@gmail.com.\n" +
                                    "\n" +
                                    "¡Gracias por utilizar PizzApp!")
                        }
                    )

                    ButtonRegister(
                        context = context,
                        isValidEmail = isValidEmail,
                        isValidPassword = isValidPassword,
                        email = email,
                        password = password,
                        nombre = nombre,
                        apellido = apellido,
                        nombreUsuario = nombreUsuario,
                        navController = navController,
                        isChecked = isChecked
                    )
                    YouHaveAccount(navController = navController)
                }
            }
        }
    }
}

@Composable
fun ImageRegister(navController: NavController){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("inicio") },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(140.dp),
            painter = painterResource(id = R.drawable.logoregistro),
            contentDescription = null,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Nombre(
    nombre: String,
    nombreChange: (String) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            value = nombre,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = nombreChange,
            label = { Text("Nombre") },
            singleLine = true,
            maxLines = 1,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Apellido(
    apellido: String,
    apellidoChange: (String) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            value = apellido,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = apellidoChange,
            label = { Text("Apellido") },
            singleLine = true,
            maxLines = 1,
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NombreUsuario(
    nombreUsuario: String,
    nombreUsuarioChange: (String) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            value = nombreUsuario,
            onValueChange = nombreUsuarioChange,
            label = { Text("Nombre de usuario") },
            singleLine = true,
            maxLines = 1,
        )
    }
}
@Composable
fun TermsAndConditions(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    showDialog: () -> Unit // Esta función abrirá el diálogo
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { newCheckedState ->
                onCheckedChange(newCheckedState)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Red
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Acepto los Términos y Condiciones",
            color = Color.Black,
            modifier = Modifier.clickable { showDialog() }
        )
    }
}

@Composable
fun TermsAndConditionsDialog(
    isOpen: Boolean,
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = onClose,
            title = {
                Text(text = "Términos y Condiciones")
            },
            text = {
                Box(modifier = Modifier.verticalScroll(rememberScrollState()).padding(8.dp).fillMaxWidth()){
                    content()
                }

            },
            confirmButton = {
                Button(
                    onClick = onClose,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                )
                {
                    Text(text = "Aceptar")
                }
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}


@Composable
fun YouHaveAccount(navController: NavController){
    Row(
        Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center) {
        Text(
            text = "¿Ya tienes una cuenta?",
            color = Color.Black,
            modifier = Modifier
                .padding(5.dp)
        )

        Text(
            text = "Iniciar sesión",
            color = Color.Blue,
            modifier = Modifier
                .padding(5.dp)
                .clickable {
                    navController.navigate("login")
                }
        )
    }

}

@Composable
fun ButtonRegister(
    context: Context,
    isValidEmail: Boolean,
    isValidPassword: Boolean,
    email: String,
    password: String,
    nombre: String,
    apellido: String,
    nombreUsuario: String,
    navController: NavController,
    isChecked: Boolean

) {
    val firestoreRepository = FirestoreRepository()

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
                if (isValidEmail && isValidPassword ){
                    if(!isChecked){
                        Toast.makeText(context, "Tienes que aceptar los términos y condiciones para hacer el registro", Toast.LENGTH_LONG).show()
                    }
                    else {
                        val user = User(nombre, apellido, email, nombreUsuario, password)
                        firestoreRepository.createUser(user,
                            onSuccess = {
                                Toast.makeText(
                                    context,
                                    "Usuario registrado con éxito",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigate("login")
                            },
                            onFailure = { e ->
                                Toast.makeText(
                                    context,
                                    "Error al registrar: ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            })
                    }
                } else {
                    Toast.makeText(context, "Revisa los campos", Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Text(text = "Registrarse")
        }
    }
}
