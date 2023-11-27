package com.example.pizzapp

import com.example.pizzapp.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference

interface IUserRepository {
    fun createUserWithAuth(user: User): Task<AuthResult>
    fun saveUserToFirestore(user: User): Task<Void>
    fun loginUser(email: String, password: String): Task<AuthResult>
    fun updateUser(userId: String, updatedData: Map<String, Any>, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun deleteUser(userId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
    fun getCurrentUser(): FirebaseUser?
    fun getUserDocument(userId: String): DocumentReference
}