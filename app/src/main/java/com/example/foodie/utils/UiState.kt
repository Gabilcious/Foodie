package com.example.foodie.utils

data class UiState <T>(
    val initialized: Boolean,
    val loading: Boolean,
    val errorMessage: TextHolder? = null,
    val successData: T? = null,
)