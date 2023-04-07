package com.example.foodie.pages.utils

import com.example.foodie.model.Restaurant
import com.example.foodie.model.RestaurantId
import com.example.foodie.model.VenueVerticalList

@Suppress("MagicNumber")
object PreviewUtils {
    fun generateRestaurants(count: Int): List<Restaurant> =
        List(count) {
            Restaurant(
                id = RestaurantId("id-$it"),
                name = "$it guys",
                shortDescription = "Amazing place to eat during the day",
                imageUrl = null,
                isFavorite = it % 3 == 1,
            )
        }

    val restaurant: Restaurant =
        generateRestaurants(count = 1).first()

    val venueList = VenueVerticalList(
        name = "all-restaurants",
        title = "All restaurants",
        restaurants = generateRestaurants(5),
    )
}