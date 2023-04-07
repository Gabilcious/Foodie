package com.example.foodie.repository.favorite

import android.content.Context
import com.example.foodie.model.RestaurantId
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val PREFERENCE_NAME = "favorite_preference_name"

class FavoritesRepositoryImpl @Inject constructor(
    @ApplicationContext private var context: Context,
) : FavoritesRepository {
    override fun setFavorite(id: RestaurantId, value: Boolean) {
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(id.value, value)
            .apply()
    }

    override fun isFavorite(id: RestaurantId): Boolean =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
            .getBoolean(id.value, false)
}