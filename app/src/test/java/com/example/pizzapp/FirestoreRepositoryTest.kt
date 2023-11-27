package com.example.pizzapp

import android.widget.Toast
import androidx.navigation.NavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.util.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class FirestoreRepositoryTest {

    @Mock
    lateinit var mockDb: FirebaseFirestore

    @Mock
    lateinit var mockAuth: FirebaseAuth

    @Mock
    lateinit var mockTaskAuthResult: Task<AuthResult>

    @Mock
    lateinit var mockDocumentRef: DocumentReference

    @Captor
    lateinit var successCaptor: ArgumentCaptor<() -> Unit>

    @Mock

    lateinit var  navController: NavController

    lateinit var repository: FirestoreRepository
    @Mock
    lateinit var mockCollection: CollectionReference

    @Mock
    lateinit var mockTask: Task<Void>


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = FirestoreRepository(mockDb, mockAuth)
        `when`(mockTask.isSuccessful).thenReturn(true)


    }

    @Test
    fun createUserWithAuth_successful() {
        // Given
        val user = User(correo = "test@email.com", password = "password", nombre = "pepita", apellido = "pro", nombreUsuario = "pepitapro")
         val mockAuthResult: AuthResult = mock(AuthResult::class.java)

        // Mock successful auth
        `when`(mockAuth.createUserWithEmailAndPassword(user.correo!!, user.password!!))
            .thenReturn(Tasks.forResult(mockAuthResult))

        // When
        val result = repository.createUserWithAuth(user)

        // Then
        org.junit.Assert.assertNotNull(result)
    }

    @Test
    fun saveUserToFirestore_successful() {
        // Given
        val user = User(correo = "test@email.com", password = "password", nombre = "pepita", apellido = "pro", nombreUsuario = "pepitapro")
        val uid = "testUid"

         val mockFirebaseUser: FirebaseUser = mock(FirebaseUser::class.java)
        `when`(mockAuth.currentUser).thenReturn(mockFirebaseUser)
        `when`(mockFirebaseUser.uid).thenReturn(uid)

        // Mock successful save to Firestore
        `when`(mockDocumentRef.set(user)).thenReturn(Tasks.forResult(null))
        val mockCollectionRef: CollectionReference = mock(CollectionReference::class.java)

        // Mock general behaviors
        `when`(mockDb.collection(anyString())).thenReturn(mockCollectionRef)
        `when`(mockCollectionRef.document(anyString())).thenReturn(mockDocumentRef)

        // When
        val result = repository.saveUserToFirestore(user)

        // Then
        org.junit.Assert.assertNotNull(result)
    }
    @Test
    fun `loginUser returns successful task when authentication succeeds`() {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val mockAuthResult = mock(AuthResult::class.java)

        `when`(mockAuth.signInWithEmailAndPassword(email, password))
            .thenReturn(Tasks.forResult(mockAuthResult))

        // When
        val result = repository.loginUser(email, password)

        // Then
        assert(result.isSuccessful)
        assert(result.result == mockAuthResult)
    }
}
