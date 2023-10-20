package com.example.pizzapp

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth

class FirestoreRepository(private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
                          private val auth: FirebaseAuth = FirebaseAuth.getInstance()) {
    // Resto de tu código


    fun createUserWithAuth(user: User): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(user.correo!!, user.password!!)
    }

    fun saveUserToFirestore(user: User): Task<Void> {
        val uid = auth.currentUser?.uid ?: throw Exception("No UID found after creation")
        return db.collection("users").document(uid).set(user)
    }


    fun loginUser(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }



    fun updateUser(userId: String, updatedData: Map<String, Any>, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
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

}
