package com.example.pizzapp

data class User(
    var nombre: String? = null,
    var apellido: String? = null,
    var correo: String? = null,
    var nombreUsuario: String? = null,
    var password: String? = null
)

data class CredentialsLogin(
    val email: String,
    val password: String
)
data class TokenResponse(
    val token: String
)
