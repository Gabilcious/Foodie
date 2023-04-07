package com.example.foodie.repository.model

import com.google.gson.annotations.SerializedName

data class ApiImage(
    @SerializedName("url")
    val url: String?,
)