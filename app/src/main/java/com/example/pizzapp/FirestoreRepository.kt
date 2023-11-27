package com.example.pizzapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.pizzapp.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference

class FirestoreRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun createUser(user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        auth.createUserWithEmailAndPassword(user.correo!!, user.password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Usuario creado en Auth, ahora guardamos en Firestore
                    val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
                    db.collection("users")
                        .document(uid)
                        .set(user)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { e -> onFailure(e) }
                } else {
                    onFailure(task.exception ?: Exception("Unknown error"))
                }
            }
    }


    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception ?: Exception("Error desconocido durante el inicio de sesión"))
                }
            }
    }

    fun updateUser(
        userId: String,
        updatedData: Map<String, Any>,
        onSuccess: () -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        db.collection("users")
            .document(userId)
            .update(updatedData)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }



    // DELETE - Eliminar un usuario por su ID
    fun deleteUser(userId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users")
            .document(userId)
            .delete()
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    fun getUserDocument(userId: String): DocumentReference {
        return db.collection("users").document(userId)
    }



}
