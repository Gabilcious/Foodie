package com.example.foodie.location

import com.example.foodie.model.Location
import kotlinx.coroutines.flow.Flow

/**
 * CurrentLocationProvider provides [CurrentLocationProvider.currentLocation] which is
 * updated in interval specified in [com.example.foodie.model.AppConfig].
 */
interface CurrentLocationProvider {
    /**
     * Hot flow containing current location of the device, updated in interval specified in
     * [com.example.foodie.model.AppConfig].
     */
    val currentLocation: Flow<Location>
}