package com.example.foodie.repository.model

import com.google.gson.annotations.SerializedName

data class ApiRestaurantsPage(
    @SerializedName("sections")
    val sections: List<ApiSection> = emptyList(),
)