package com.example.foodie.repository.favorite

import com.example.foodie.model.RestaurantId

interface FavoritesRepository {
    fun setFavorite(id: RestaurantId, value: Boolean)

    fun isFavorite(id: RestaurantId): Boolean
}