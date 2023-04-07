package com.example.foodie.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher
import javax.inject.Inject

data class AppDispatchers @Inject constructor(
    val main: MainCoroutineDispatcher,
    val io: CoroutineDispatcher,
    val default: CoroutineDispatcher,
)