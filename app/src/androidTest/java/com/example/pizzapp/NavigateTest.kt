package com.example.pizzapp

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.example.pizzapp.Screen.MyProfileScreen
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*
import org.mockito.ArgumentMatchers.any

class NavigateTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    private val mockFirebaseAuth = mock(FirebaseAuth::class.java)
    private val mockFirestore = mock(FirebaseFirestore::class.java)

    @Before
    fun setUpNavHost(){
        `when`(mockFirebaseAuth.currentUser).thenReturn(mock(FirebaseUser::class.java))
        `when`(mockFirestore.collection(any())).thenReturn(mock(CollectionReference::class.java))

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())


    }


    @Test
    fun verificar_registro() {
        composeTestRule.onNodeWithText("Registrarse").assertIsDisplayed()
    }

    // No se paso
    @Test
    fun registro_a_iniciarSesion(){
        val mockDocumentReference = mock(DocumentReference::class.java)
        `when`(mockFirestore.collection("users").document(any())).thenReturn(mockDocumentReference)

        val email = "test@test.com"
        val password = "testpassword"
        val nombre = "John"
        val apellido = "Doe"
        val nombreUsuario = "johndoe"

        // Simular el llenado de los campos en la pantalla de registro
        composeTestRule.setContent {
            RegisterScreen(navController = navController)
        }
        composeTestRule.mainClock.advanceTimeBy(1000)  // waits for 1 second

        // Establecer los valores en los campos
        composeTestRule.onNodeWithText("Nombre").performTextInput(nombre)
        composeTestRule.onNodeWithText("Apellido").performTextInput(apellido)
        composeTestRule.onNodeWithText("Email").performTextInput(email)
        composeTestRule.onNodeWithText("Nombre de usuario").performTextInput(nombreUsuario)
        composeTestRule.onNodeWithText("Contraseña").performTextInput(password)

        // Simular el registro exitoso en FirebaseAuth
        `when`(mockFirebaseAuth.createUserWithEmailAndPassword(email, password))
            .thenReturn(mock(Task::class.java) as Task<AuthResult>?)

        // Realizar clic en el botón de registro
        composeTestRule.onNodeWithText("Registrarse").performClick()

        // Verificar si se llamó a Firestore para guardar los datos del usuario
        verify(mockFirestore.collection("users").document(any())).set(any(User::class.java))

        // Verificar si se redirige a la ruta "login"
        val route = navController.currentBackStackEntry?.destination?.route
        Assert.assertEquals(route, "login")
    }
}