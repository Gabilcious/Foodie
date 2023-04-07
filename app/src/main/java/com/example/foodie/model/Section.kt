package com.example.foodie.model

sealed class Section

data class VenueVerticalList(
    val name: String,
    val title: String,
    val restaurants: List<Restaurant>,
) : Section()