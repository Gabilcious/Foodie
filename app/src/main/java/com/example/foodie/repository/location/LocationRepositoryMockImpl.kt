package com.example.foodie.repository.location

import com.example.foodie.model.Location
import javax.inject.Inject

class LocationRepositoryMockImpl @Inject constructor() : LocationRepository {
    private var idx = 0

    private val locations = listOf(
        Location("60.169418", "24.931618"),
        Location("60.169818", "24.932906"),
        Location("60.170005", "24.935105"),
        Location("60.169108", "24.936210"),
        Location("60.168355", "24.934869"),
        Location("60.170187", "24.930599"),
        Location("60.167560", "24.932562"),
        Location("60.168254", "24.931532"),
        Location("60.169012", "24.930341"),
        Location("60.170085", "24.929569"),
    )

    override fun getCurrentLocation(): Location = locations[idx].also {
        idx = (idx + 1) % locations.size
    }
}