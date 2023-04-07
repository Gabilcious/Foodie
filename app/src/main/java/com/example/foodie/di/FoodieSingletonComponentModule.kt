package com.example.foodie.di

import com.example.foodie.location.CurrentLocationProvider
import com.example.foodie.location.CurrentLocationProviderImpl
import com.example.foodie.model.AppConfig
import com.example.foodie.repository.favorite.FavoritesRepository
import com.example.foodie.repository.favorite.FavoritesRepositoryImpl
import com.example.foodie.repository.location.LocationRepository
import com.example.foodie.repository.location.LocationRepositoryMockImpl
import com.example.foodie.repository.pages.restaurants.RestaurantsPageRepository
import com.example.foodie.repository.pages.restaurants.RestaurantsPageRepositoryImpl
import com.example.foodie.timer.AppTimer
import com.example.foodie.timer.AppTimerImpl
import com.example.foodie.utils.AppDispatchers
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
internal abstract class FoodieSingletonComponentModule {

    @Binds
    abstract fun bindRestaurantRepository(impl: RestaurantsPageRepositoryImpl): RestaurantsPageRepository

    @Binds
    abstract fun bindFavoriteRepository(impl: FavoritesRepositoryImpl): FavoritesRepository

    @Binds
    abstract fun bindLocationRepository(impl: LocationRepositoryMockImpl): LocationRepository

    @Binds
    abstract fun bindAppTimer(impl: AppTimerImpl): AppTimer

    @Binds
    abstract fun bindCurrentLocationProvider(impl: CurrentLocationProviderImpl): CurrentLocationProvider

    companion object {
        @Provides
        fun provideAppConfig(): AppConfig = AppConfig(
            pagesBaseUrl = "https://restaurant-api.wolt.com/v1/pages/",
            locationUpdateIntervalInSeconds = 10,
            numberOfRestaurantsToDisplay = 15,
        )

        @Provides
        fun provideAppDispatchers(): AppDispatchers =
            AppDispatchers(
                main = Dispatchers.Main,
                io = Dispatchers.IO,
                default = Dispatchers.Default,
            )
    }
}