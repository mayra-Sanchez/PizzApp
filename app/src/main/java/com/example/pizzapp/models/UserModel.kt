package com.example.pizzapp.models

data class User(
    var nombre: String? = null,
    var apellido: String? = null,
    var correo: String? = null,
    var nombreUsuario: String? = null,
    var password: String? = null,
    var photoUrl: String? = null // Agrega la propiedad photoUrl
)

data class Pizzerias(
    val name: String,
    val address: String
)
data class CredentialsLogin(
    val email: String,
    val password: String
)
data class TokenResponse(
    val token: String
)
