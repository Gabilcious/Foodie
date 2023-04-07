package com.example.foodie.location

import com.example.foodie.model.AppConfig
import com.example.foodie.model.Location
import com.example.foodie.repository.location.LocationRepository
import com.example.foodie.timer.AppTimer
import com.example.foodie.utils.AppDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject
import javax.inject.Singleton

private const val MILLIS_IN_SECOND = 1_000L

@Singleton
class CurrentLocationProviderImpl @Inject constructor(
    appConfig: AppConfig,
    private val locationRepository: LocationRepository,
    appTimer: AppTimer,
    dispatchers: AppDispatchers,
) : CurrentLocationProvider {

    private val coroutineScope = CoroutineScope(SupervisorJob() + dispatchers.default)

    private var nextUpdateTime = 0L
    private val updateIntervalInMillis = appConfig.locationUpdateIntervalInSeconds * MILLIS_IN_SECOND

    override val currentLocation: SharedFlow<Location> =
        appTimer.tic
            .onCompletion { coroutineScope.cancel() }
            .transform {
                if (nextUpdateTime <= it) {
                    nextUpdateTime = it + updateIntervalInMillis
                    val location = locationRepository.getCurrentLocation()
                    emit(location)
                }
            }
            .shareIn(
                scope = coroutineScope,
                started = SharingStarted.Eagerly,
                replay = 1,
            )
}