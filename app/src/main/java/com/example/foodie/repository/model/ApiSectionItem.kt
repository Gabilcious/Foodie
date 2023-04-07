package com.example.foodie.repository.model

import com.google.gson.annotations.SerializedName

data class ApiSectionItem(
    @SerializedName("venue")
    val venue: ApiVenue?,
    @SerializedName("image")
    val image: ApiImage?,
)