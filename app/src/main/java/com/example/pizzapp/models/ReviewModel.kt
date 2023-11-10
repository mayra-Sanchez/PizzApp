package com.example.pizzapp.models

data class Review(
    val reviewRestaurant: String,
    val placeName: String,
    val placeAddress: String,
    val starRating: Int,
    val pizzaType: String
)