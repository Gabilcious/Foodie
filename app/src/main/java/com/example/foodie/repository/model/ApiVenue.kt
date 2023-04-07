package com.example.foodie.repository.model

import com.google.gson.annotations.SerializedName

data class ApiVenue(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("short_description")
    val shortDescription: String?,
)