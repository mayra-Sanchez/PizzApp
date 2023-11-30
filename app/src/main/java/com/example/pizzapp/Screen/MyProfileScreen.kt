package com.example.pizzapp.Screen

import android.content.Context
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pizzapp.R
import com.example.pizzapp.decodeJWT
import com.example.pizzapp.navbar.Navbar
import android.Manifest
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.isGranted
import java.io.File
import java.util.concurrent.Executor


@Composable
fun MyProfileScreen(navController: NavController) {
    var isCameraOpen by remember { mutableStateOf(false) } // Asume que el token JWT es pasado como argumento
    val context = LocalContext.current
//    val userData = decodeJWT(jwtToken) // Decodifica la información del usuario desde el token JWT
//      Variables para almacenar los datos del usuario
//    var email by remember { mutableStateOf(userData?.get("email") as? String ?: "") }
//    var isValidEmail by remember { mutableStateOf(false) }
//    var nombreUsuario by remember { mutableStateOf(userData?.get("nombreUsuario") as? String ?: "") }
//    var isValidPassword by remember { mutableStateOf(false) }
//    var nombre by remember { mutableStateOf(userData?.get("nombre") as? String ?: "") }
//    var apellido by remember { mutableStateOf(userData?.get("apellido") as? String ?: "") }

    var email = "laurita@gmail.com"
    var nombre = "laura"
    var apellido = "jaimes"
    var nombreUsuario = "laurita123"

    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { photoUri = it }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color(255, 204, 51, 255))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Navbar(navController)
                Text(
                    text = "Mi perfil",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                )
                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // Esto asigna un peso de 1 a LazyColumn
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color(255, 204, 51, 255))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth().padding(10.dp)
                            ) {
                                ProfileImage(
                                    imageUri = photoUri,
                                    onImageSelected = { galleryLauncher.launch("image/*")
                                    },
                                    onCameraSelected = { isCameraOpen = true } // Maneja la selección de cámara
                                )
                                Spacer(modifier = Modifier.height(16.dp))
//                                Text(
//                                    text = "Nombre",
//                                    fontSize = 13.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    modifier = Modifier
//                                        .align(Alignment.Start)
//                                        .padding(8.dp)
//                                )
                                ActualizarTexto(nombre, onValueChange = { nombre = it }, "Nombre")
                                Spacer(modifier = Modifier.height(16.dp))
//                                Text(
//                                    text = "Apellido",
//                                    fontSize = 13.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    modifier = Modifier
//                                        .align(Alignment.Start)
//                                        .padding(8.dp)
//                                )
                                ActualizarTexto(apellido, onValueChange = { apellido = it }, "Apellido")
                                Spacer(modifier = Modifier.height(16.dp))
//                                Text(
//                                    text = "Nombre de usuario",
//                                    fontSize = 13.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    modifier = Modifier
//                                        .align(Alignment.Start)
//                                        .padding(8.dp)
//                                )
                                ActualizarTexto(
                                    nombreUsuario,
                                    onValueChange = { nombreUsuario = it }, "Nombre de usuario")
                                Spacer(modifier = Modifier.height(16.dp))
//                                Text(
//                                    text = "Email",
//                                    fontSize = 13.sp,
//                                    fontWeight = FontWeight.Bold,
//                                    modifier = Modifier
//                                        .align(Alignment.Start)
//                                        .padding(8.dp)
//                                )
                                ActualizarTexto(email, onValueChange = { email = it }, "Email")

                            }
                        }
                    }
                }

                ButtonUpdate(context = context,
                    email = email,
                    nombre = nombre,
                    apellido = apellido,
                    nombreUsuario = nombreUsuario,
                    navController = navController
                )
            }
        }
    }
    if (isCameraOpen) {
        HomeCamera(navController = navController)
    }
}

@Composable
fun ProfileImage(
    imageUri: Uri?,
    onImageSelected: () -> Unit,
    onCameraSelected: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    val onImageOptionSelected: (ImageOption) -> Unit = { imageOption ->
        when (imageOption) {
            ImageOption.Camera -> {
                onCameraSelected()
                showDialog = false
            }
            ImageOption.Gallery -> {
                onImageSelected()
                showDialog = false
            }
        }
        showDialog = false
    }
    Box(modifier = Modifier
        .size(120.dp)
        .clip(CircleShape)
        .background(Color(255,255,255))
        .clickable { showDialog = true },
        contentAlignment = Alignment.Center
    ){
        imageUri?.let {
            androidx.compose.foundation.Image(
                painter = rememberImagePainter(it),
                contentDescription = "Imagen de perfil",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape))
        }
        if(showDialog){
            ImageSelectionDialog(onImageOptionSelected)
        }
    }
}

enum class ImageOption {
    Camera,
    Gallery
}

@Composable
fun ImageSelectionDialog(
    onImageOptionSelected: (ImageOption) -> Unit,
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = "Seleccionar imagen") },
        text = {
            Column {
                Text(
                    text = "Seleccionar desde:",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Button(
                    onClick = { onImageOptionSelected(ImageOption.Camera) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text(text = "Cámara")
                }
                Button(
                    onClick = { onImageOptionSelected(ImageOption.Gallery) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Galería")
                }
            }
        },
        confirmButton = {},
        dismissButton = {}
    )
}

//TODO LO DE CAMARA ---------------------------------------------------------------------------------------
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeCamera(navController: NavController) {
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val context = LocalContext.current
    val cameraController = remember {
        LifecycleCameraController(context)
    }
    val lifecycle = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }
    Scaffold(modifier = Modifier.fillMaxSize(), floatingActionButton = {
        FloatingActionButton(onClick = {
            val executor = ContextCompat.getMainExecutor(context)
            takePicture(
                cameraController,
                executor,
                context,
                navController,
                updateProfileImage = { uri ->
                    updateProfileImageAndNavigate(uri, navController)
                })
        }) {
            Text(text = "Tomar foto")
        }
    }) {
        if (permissionState.status.isGranted) {
            CamaraComposable(cameraController, lifecycle, modifier = Modifier.padding(it))
        } else {
            Text(text = "Permiso Denegado!", modifier = Modifier.padding(it))
        }
    }
}
fun updateProfileImageAndNavigate(uri: Uri, navController: NavController) {
    Log.d("CameraDEMO", "updateProfileImageAndNavigate: uri = $uri")
    navController.navigate("mi_perfil")
}

private fun takePicture(
    cameraController: LifecycleCameraController,
    executor: Executor,
    context: Context,
    navController: NavController,
    updateProfileImage: (Uri) -> Unit
) {
    val outputDirectory = File(context.filesDir, "imagenes_tomadas") // Directorio para guardar las imágenes
    outputDirectory.mkdirs() // Crea el directorio si no existe

    val file = File.createTempFile("picture", ".jpg", outputDirectory) // Crea el archivo en el directorio

    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()

    cameraController.takePicture(
        outputFileOptions,
        executor,
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                outputFileResults.savedUri?.let { uri ->
                    Log.d("CameraDemo", "Imagen guardada: uri = $uri")
                    Toast.makeText(context, "Imagen guardada", Toast.LENGTH_LONG).show()
                    updateProfileImage(uri)
                }
            }
            override fun onError(exception: ImageCaptureException) {
                // Manejar el error
                val msg = "Error al guardar la imagen: ${exception.message}"
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }
    )
}

@Composable
fun CamaraComposable(
    cameraController: LifecycleCameraController,
    lifecycle: LifecycleOwner,
    modifier: Modifier = Modifier,
) {
    cameraController.bindToLifecycle(lifecycle)
    AndroidView(modifier = modifier, factory = { context ->
        val previewView = PreviewView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
            )
        }
        previewView.controller = cameraController

        previewView })
}
//-----------------------------------------------------------------------------------------------------------------------------------
@Composable
fun ButtonUpdate(
    context: Context,
    email: String,
    nombre: String,
    apellido: String,
    nombreUsuario: String,
    navController: NavController
){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {

            } ,
            colors = ButtonDefaults.buttonColors(Color(236, 83, 76))
        ) {
            Text(text = "Actualizar")
        }

    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActualizarTexto(
    value: String,
    onValueChange: (String) -> Unit,
    label: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        label = { Text(label) },
        singleLine = true,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth().background(Color.White, RoundedCornerShape(5.dp))
            .padding(8.dp)
    )
}



