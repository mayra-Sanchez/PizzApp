package com.example.pizzapp.models

data class User(
    var nombre: String? = null,
    var apellido: String? = null,
    var email: String? = null,
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

data class resetPassword(
    var email:String? = null
)

data class response(
    var response: String
)

data class verifyCode(
    var email:String? = null,
    var code: String?=null
)

data class chagenPassword(
    var password: String?=null
)
