package com.example.foodie.model

data class AppConfig(
    /**
     * Base url to fetch pages from backend.
     *
     * To fetch some particular page it will be concatenated with path indicating name of
     * that page.
     */
    val pagesBaseUrl: String,

    /**
     * How often content, which depends on the device location, should be refreshed.
     */
    val locationUpdateIntervalInSeconds: Long,

    /**
     * Number of restaurants that are displayed in the vertical section in the restaurants page.
     */
    val numberOfRestaurantsToDisplay: Int,
)