package com.example.foodie.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitServiceFactory<T> @Inject constructor() {
    fun create(baseUrl: String, service: Class<T>): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(service)
}