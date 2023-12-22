package com.example.pizzapp.Screen

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.pizzapp.ImageRepository
import com.example.pizzapp.RetrofitClient
import com.example.pizzapp.UserViewModel
import com.example.pizzapp.navbar.Navbar
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import java.io.File
import java.util.concurrent.Executor
import com.example.pizzapp.decodeJWT
import com.example.pizzapp.models.TokenResponse
import com.example.pizzapp.models.User
import com.example.pizzapp.models.UserUpdate
import com.example.pizzapp.models.saveImage
import com.google.gson.Gson
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.FileOutputStream
import java.io.IOException


@Composable
fun MyProfileScreen(navController: NavController, jwtToken: String, userUpdateJson: String) {
    val userUpdate = Gson().fromJson(userUpdateJson, UserUpdate::class.java) // Deserializar el JSON

    var isCameraOpen by remember { mutableStateOf(false) } // Asume que el token JWT es pasado como argumento
    val context = LocalContext.current
    val userData = decodeJWT(jwtToken) // Decodifica la información del usuario desde el token JWT
    var email by remember { mutableStateOf(userUpdate.email ?: "") }
    var nombreUsuario by remember { mutableStateOf(userUpdate.nombreUsuario ?: "") }
    var nombre by remember { mutableStateOf(userUpdate.nombre ?: "") }
    var apellido by remember { mutableStateOf(userUpdate.apellido ?: "") }

    var photoUri by remember { mutableStateOf<Uri?>(null) }
    var photoBytes by remember { mutableStateOf<String?>(null) }

    // Manejar la imagen de perfil
    val profilePictureBytes = ImageRepository.getImage(email)
    if (profilePictureBytes != null) {
        photoBytes = profilePictureBytes
    } else {
        photoUri = profilePictureBytes?.let { byteArrayToTempUri(context, it) }
    }


    // Variables para almacenar los datos del usuario

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { photoUri = it
            cargarImagenDesdeUri(it, context,jwtToken) // Llamada a la función de carga
        }
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
                Navbar(navController, jwtToken )
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
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    ProfileImage(
                                        imageUri = photoUri,
                                        onImageSelected = { galleryLauncher.launch("image/*") },
                                        onCameraSelected = { isCameraOpen = true },
                                        imageByte = photoBytes
                                        )
                                }
                                Spacer(modifier = Modifier.height(10.dp))

                                ActualizarTexto(nombre, onValueChange = { nombre = it }, "Nombre")
                                Spacer(modifier = Modifier.height(10.dp))

                                ActualizarTexto(apellido, onValueChange = { apellido = it }, "Apellido")
                                Spacer(modifier = Modifier.height(10.dp))

                                ActualizarTexto(
                                    nombreUsuario,
                                    onValueChange = { nombreUsuario = it }, "Nombre de usuario")
                                Spacer(modifier = Modifier.height(10.dp))

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
                    navController = navController,
                    jwtToken= jwtToken
                )
            }
        }
    }
    if (isCameraOpen) {
        HomeCamera(navController = navController) { uri ->
            photoUri = uri
            cargarImagenDesdeUri(uri, context,jwtToken) // Llamada a la función de carga

            isCameraOpen = false
        }
    }
}

fun byteArrayToTempUri(context: Context, byteArray: ByteArray): Uri? {
    return try {
        // Crear un archivo temporal
        val tempFile = File.createTempFile("profile_picture", null, context.cacheDir).apply {
            deleteOnExit()
        }
        FileOutputStream(tempFile).use { fos ->
            fos.write(byteArray)
        }

        // Obtener la Uri del archivo temporal
        FileProvider.getUriForFile(context, "${context.packageName}.provider", tempFile)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}


@Composable
fun ProfileImage(
    imageUri: Uri?,
    onImageSelected: () -> Unit,
    onCameraSelected: () -> Unit,
    imageByte: String?
) {
    var context= LocalContext.current;
    var showDialog by remember { mutableStateOf(false) }

    val onImageOptionSelected: (ImageOption) -> Unit = { imageOption ->
        showDialog = false
        when (imageOption) {
            ImageOption.Camera -> onCameraSelected()
            ImageOption.Gallery -> onImageSelected()
        }
    }

    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .background(Color(255, 255, 255))
            .clickable { showDialog = true },
        contentAlignment = Alignment.Center
    ) {
        when {
            // Si se dispone de un URI, carga la imagen desde el URI
            imageUri != null -> {
                androidx.compose.foundation.Image(
                    painter = rememberImagePainter(imageUri),
                    contentDescription = "Imagen de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }
            // Si se dispone de bytes, convierte los bytes en Bitmap y luego muestra la imagen
            imageByte != null -> {
                val byteArray = Base64.decode(imageByte, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
                androidx.compose.foundation.Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Imagen de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }
        }
        if (showDialog) {
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
                    onClick = { onImageOptionSelected(ImageOption.Camera)

                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = ButtonDefaults.buttonColors(Color(236, 83, 76))
                ) {
                    Text(text = "Cámara")
                }
                Button(
                    onClick = { onImageOptionSelected(ImageOption.Gallery) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(236, 83, 76))
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
fun HomeCamera(navController: NavController, updateProfileImage: (Uri) -> Unit) {
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
                updateProfileImage = updateProfileImage
            )
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
fun updateProfileImageAndNavigate(uri: Uri, navController: NavController, onImageUpdated: (Uri) -> Unit) {
    Log.d("CameraDEMO", "updateProfileImageAndNavigate: uri = $uri")
    onImageUpdated(uri)
    navController.navigate("mi_perfil")
}

fun obtenerBytesDesdeUri(context: Context, uri: Uri): ByteArray {
    val inputStream = context.contentResolver.openInputStream(uri)
    return inputStream?.readBytes() ?: byteArrayOf()
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
    navController: NavController,
    jwtToken:String
){
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                val userUpdate= User(
                    nombre,
                    apellido,
                    email,
                    nombreUsuario)
                RetrofitClient.apiService.updateUser("Bearer "+jwtToken,userUpdate).enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {

                            Toast.makeText(context, "Actualización de datos con éxito", Toast.LENGTH_LONG).show()
                            navController.navigate("pagina_principal/${jwtToken}")

                        } else {
                            // Manejo del caso en que la respuesta no es exitosa
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

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        // Manejo del caso en que la llamada falla
                        println(t.message)
                        Toast.makeText(context, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
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
    TextField(
        value = value,
        onValueChange = onValueChange,
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



fun cargarImagenDesdeUri(uri: Uri, context: Context, jwtToken: String) {
    val imageBytes = obtenerBytesDesdeUri(context, uri)


    val saveImage = saveImage(imageBytes)

    RetrofitClient.apiService.updateImage("Bearer "+jwtToken,saveImage).enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            if (response.isSuccessful) {
                Toast.makeText(context, "Imagen cargada con éxito", Toast.LENGTH_LONG).show()
            } else {
                // Manejo del caso en que la respuesta no es exitosa
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

        override fun onFailure(call: Call<User>, t: Throwable) {
            // Manejo del caso en que la llamada falla
            println(t.message)
            Toast.makeText(context, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
        }
    })
}
