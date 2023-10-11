package com.example.pizzapp

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun createUser(user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                onSuccess()
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
                onFailure(e)
            }
    }

    fun getUserByNombreUsuario(nombreUsuario: String, onSuccess: (User) -> Unit, onFailure: (Exception) -> Unit) {
        db.collection("users")
            .whereEqualTo("nombreUsuario", nombreUsuario)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.size() > 0) {
                    val user = documents.documents[0].toObject(User::class.java)!!
                    onSuccess(user)
                } else {
                    onFailure(Exception("No user found"))
                }
            }
            .addOnFailureListener { e ->
                onFailure(e)
            }
    }

    fun loginUser(nombreUsuario: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val firestoreRepository = FirestoreRepository()

        firestoreRepository.getUserByNombreUsuario(nombreUsuario, { user ->
            if (user.password == password) { // NUEVAMENTE, almacenar contraseñas en texto plano no es seguro.
                onSuccess()
            } else {
                onFailure("Contraseña incorrecta")
            }
        }, {
            onFailure("Usuario no encontrado")
        })
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
