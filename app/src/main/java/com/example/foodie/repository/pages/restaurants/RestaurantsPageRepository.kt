package com.example.foodie.repository.pages.restaurants

import com.example.foodie.model.Location
import com.example.foodie.model.Section

interface RestaurantsPageRepository {
    suspend fun getSections(location: Location): List<Section>
}
