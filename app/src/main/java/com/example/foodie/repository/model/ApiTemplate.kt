package com.example.foodie.repository.model

import com.google.gson.annotations.SerializedName

enum class ApiTemplate(val value: String) {
    @SerializedName("venue-vertical-list")
    VENUE_VERTICAL_LIST("venue-vertical-list"),

    @SerializedName("other")
    OTHER("other"),
}