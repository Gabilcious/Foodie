package com.example.foodie.repository.pages

import com.example.foodie.repository.model.ApiRestaurantsPage
import retrofit2.http.GET
import retrofit2.http.Query

interface PagesService {
    @GET("restaurants")
    suspend fun getRestaurantsPage(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
    ): ApiRestaurantsPage
}