package com.example.foodie.model

data class AppConfig(
    val pagesBaseUrl: String,
    val locationUpdateIntervalInSeconds: Long,
    val numberOfRestaurantsToDisplay: Int,
)