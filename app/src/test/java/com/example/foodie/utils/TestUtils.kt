package com.example.foodie.utils

import com.example.foodie.model.AppConfig
import com.example.foodie.model.Location
import com.example.foodie.model.Restaurant
import com.example.foodie.model.RestaurantId
import com.example.foodie.model.VenueVerticalList
import com.example.foodie.repository.model.ApiImage
import com.example.foodie.repository.model.ApiSection
import com.example.foodie.repository.model.ApiSectionItem
import com.example.foodie.repository.model.ApiTemplate
import com.example.foodie.repository.model.ApiVenue

@Suppress("MemberVisibilityCanBePrivate")
object TestUtils {

    // APP CONFIG
    const val pagesBaseUrl = "https://baseUrl.com"
    const val locationUpdateIntervalInSeconds = 1L
    const val locationUpdateIntervalInMillis = 1L * 1_000
    const val numberOfRestaurantsToDisplay = 15

    val appConfig = AppConfig(
        pagesBaseUrl = pagesBaseUrl,
        locationUpdateIntervalInSeconds = locationUpdateIntervalInSeconds,
        numberOfRestaurantsToDisplay = numberOfRestaurantsToDisplay,
    )

    // LOCATION
    val location = Location("test-latitude", "test-longitude")

    // SECTION
    const val sectionTestName = "test-name"
    const val sectionTestTitle = "test-title"

    val verticalApiSection = ApiSection(
        name = sectionTestName,
        title = sectionTestTitle,
        items = generateSectionItemAPI(5),
        template = ApiTemplate.VENUE_VERTICAL_LIST,
    )
    val verticalSection = VenueVerticalList(
        name = sectionTestName,
        title = sectionTestTitle,
        restaurants = generateRestaurants(5),
    )

    // RESTAURANT
    fun generateRestaurants(count: Int): List<Restaurant> =
        List(count) {
            Restaurant(
                id = RestaurantId("test-id-$it"),
                name = "test-name-$it",
                shortDescription = "test-description-$it",
                imageUrl = "test-url-$it",
                isFavorite = false,
            )
        }

    val restaurant: Restaurant = generateRestaurants(1).first()

    fun generateSectionItemAPI(count: Int): List<ApiSectionItem> =
        List(count) {
            ApiSectionItem(
                ApiVenue(
                    id = "test-id-$it",
                    name = "test-name-$it",
                    shortDescription = "test-description-$it",
                ),
                ApiImage(
                    url = "test-url-$it",
                ),
            )
        }
}