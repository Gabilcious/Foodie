package com.example.foodie.repository.pages.restaurants

import com.example.foodie.model.AppConfig
import com.example.foodie.model.Location
import com.example.foodie.model.Section
import com.example.foodie.repository.RetrofitServiceFactory
import com.example.foodie.repository.pages.PagesService
import javax.inject.Inject

class RestaurantsPageRepositoryImpl @Inject constructor(
    appConfig: AppConfig,
    factory: RetrofitServiceFactory<PagesService>,
) : RestaurantsPageRepository {

    private val pagesService =
        factory.create(appConfig.pagesBaseUrl, PagesService::class.java)

    override suspend fun getSections(location: Location): List<Section> =
        pagesService
            .getRestaurantsPage(location.latitude, location.longitude)
            .sections
            .mapNotNull { it.mapToSectionOrNull() }
}