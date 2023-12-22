package com.example.pizzapp.models

import org.threeten.bp.Instant

data class User(
    var nombre: String? = null,
    var apellido: String? = null,
    var email: String? = null,
    var nombreUsuario: String? = null,
    var password: String? = null,
    var profilePicture: String? = null // Agrega la propiedad photoUrl
)

data class Pizzerias(
    val name: String,
    val address: String
)
data class CredentialsLogin(
    val email: String,
    val password: String
)

data class Review(
    var dateCreation: Instant? = null,
    var author: String? = null,
    var descripcion: String? = null,
    var calificacion: Int? = null,
    var email: String? = null,
    var restaurante: String? = null
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

data class saveImage(
    var profilePicture: ByteArray
)


data class UserUpdate(
    var nombre: String? = null,
    var apellido: String? = null,
    var email: String? = null,
    var nombreUsuario: String? = null,
    var profilePicture: String? = null // Agrega la propiedad photoUrl
)

