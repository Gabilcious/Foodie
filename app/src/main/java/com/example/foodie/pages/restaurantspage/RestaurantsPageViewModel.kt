package com.example.foodie.pages.restaurantspage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodie.R
import com.example.foodie.location.CurrentLocationProvider
import com.example.foodie.model.AppConfig
import com.example.foodie.model.Location
import com.example.foodie.model.Restaurant
import com.example.foodie.model.VenueVerticalList
import com.example.foodie.repository.favorite.FavoritesRepository
import com.example.foodie.repository.pages.restaurants.RestaurantsPageRepository
import com.example.foodie.utils.ResourceHolder
import com.example.foodie.utils.UiState
import com.example.foodie.utils.toTextHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsPageViewModel @Inject constructor(
    private val appConfig: AppConfig,
    private val restaurantsPageRepository: RestaurantsPageRepository,
    private val favoritesRepository: FavoritesRepository,
    private val currentLocationProvider: CurrentLocationProvider,
) : ViewModel() {

    val state = mutableStateOf(
        UiState<VenueVerticalList>(
            initialized = false,
            loading = false,
        ),
    )

    private var lastLocation: Location? = null

    init {
        viewModelScope.launch {
            currentLocationProvider.currentLocation.collect {
                lastLocation = it
                updateVerticalSection(it)
            }
        }
    }

    fun onFavoriteClick(restaurant: Restaurant, value: Boolean) {
        favoritesRepository.setFavorite(restaurant.id, value)
        state.value = state.value.copy(
            successData = state.value.successData?.let(::prepareVerticalSection),
        )
    }

    fun onRetryClick() {
        lastLocation?.let(::updateVerticalSection)
    }

    private fun updateVerticalSection(location: Location) {
        viewModelScope.launch {
            try {
                state.value = state.value.copy(
                    loading = true,
                    errorMessage = null,
                )
                val section = restaurantsPageRepository
                    .getSections(location)
                    .filterIsInstance<VenueVerticalList>()
                    .firstOrNull()
                if (section != null && section.restaurants.isNotEmpty()) {
                    state.value = UiState(
                        initialized = true,
                        loading = false,
                        successData = prepareVerticalSection(section),
                    )
                } else {
                    state.value = UiState(
                        initialized = true,
                        loading = false,
                        errorMessage = ResourceHolder(R.string.error_no_restaurants_found),
                    )
                }
            } catch (e: Exception) {
                state.value = UiState(
                    initialized = true,
                    loading = false,
                    errorMessage = e.message?.toTextHolder()
                        ?: ResourceHolder(R.string.error_something_went_wrong_try_again_later),
                )
            }
        }
    }

    private fun prepareVerticalSection(section: VenueVerticalList): VenueVerticalList {
        return section.copy(
            restaurants = section.restaurants
                .take(appConfig.numberOfRestaurantsToDisplay)
                .map {
                    it.copy(isFavorite = favoritesRepository.isFavorite(it.id))
                },
        )
    }
}