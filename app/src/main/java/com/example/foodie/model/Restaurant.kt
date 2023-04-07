package com.example.foodie.model

data class Restaurant(
    val id: RestaurantId,
    val name: String,
    val shortDescription: String,
    val imageUrl: String?,
    val isFavorite: Boolean = false,
)

@JvmInline
value class RestaurantId(val value: String)