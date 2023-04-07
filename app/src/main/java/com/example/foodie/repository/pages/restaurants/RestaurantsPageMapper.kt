package com.example.foodie.repository.pages.restaurants

import com.example.foodie.model.Restaurant
import com.example.foodie.model.RestaurantId
import com.example.foodie.model.Section
import com.example.foodie.model.VenueVerticalList
import com.example.foodie.repository.model.ApiSection
import com.example.foodie.repository.model.ApiSectionItem
import com.example.foodie.repository.model.ApiTemplate

fun ApiSection.mapToSectionOrNull(): Section? {
    return when (template) {
        ApiTemplate.VENUE_VERTICAL_LIST -> VenueVerticalList(
            name = name ?: return null,
            title = title ?: return null,
            restaurants = items.mapNotNull { it.mapToRestaurantOrNull() },
        )
        else -> null
    }
}

private fun ApiSectionItem.mapToRestaurantOrNull(): Restaurant? {
    return Restaurant(
        id = venue?.id?.let(::RestaurantId) ?: return null,
        name = venue.name ?: return null,
        shortDescription = venue.shortDescription?.trim() ?: "",
        imageUrl = image?.url,
    )
}