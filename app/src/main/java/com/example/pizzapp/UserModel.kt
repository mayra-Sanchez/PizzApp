package com.example.pizzapp

data class User(
    val nombre: String,
    val apellido: String,
    val correo: String,
    val nombreUsuario: String,
    val password: String  // Nota: en una aplicación real, nunca almacenes contraseñas sin cifrar.
)
