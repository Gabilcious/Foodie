package com.example.foodie.repository.location

import com.example.foodie.model.Location

interface LocationRepository {
    fun getCurrentLocation(): Location
}