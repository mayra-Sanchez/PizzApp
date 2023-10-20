package com.example.pizzapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class NavigateTestLogin {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    private val mockFirebaseAuth = Mockito.mock(FirebaseAuth::class.java)
    private val mockFirestore = Mockito.mock(FirebaseFirestore::class.java)

    @Before
    fun setUpNavHost(){
        Mockito.`when`(mockFirebaseAuth.currentUser)
            .thenReturn(Mockito.mock(FirebaseUser::class.java))
        Mockito.`when`(mockFirestore.collection(ArgumentMatchers.any()))
            .thenReturn(Mockito.mock(CollectionReference::class.java))

        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.navigatorProvider.addNavigator(ComposeNavigator())

        composeTestRule.setContent {
            LoginScreen(navController = navController)
        }
    }

    @Test
    fun verificar_registro() {
        composeTestRule.onNodeWithText("Iniciar sesión").assertIsDisplayed()
    }

    @Test
    fun iniciar_sesion_y_navegar_a_pagina_inicial() {
        val email = "jesus@gmail.com"
        val password = "123456"

        // Establecer los valores en los campos
        composeTestRule.onNodeWithText("Email").performTextInput(email)
        composeTestRule.onNodeWithText("Contraseña").performTextInput(password)

        // Simular el inicio de sesión exitoso en FirebaseAuth
        `when`(mockFirebaseAuth.signInWithEmailAndPassword(email, password))
            .thenReturn(mock(Task::class.java) as Task<AuthResult>?)

        // Realizar clic en el botón de inicio de sesión
        composeTestRule.onNodeWithText("Iniciar sesión").performClick()

        // Esperar a que se procesen las operaciones asíncronas
        composeTestRule.waitForIdle()

    }

}