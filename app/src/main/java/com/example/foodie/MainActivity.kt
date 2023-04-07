package com.example.foodie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.foodie.pages.restaurantspage.RestaurantsPageViewModel
import com.example.foodie.pages.restaurantspage.view.MainView
import com.example.foodie.theme.FoodieTheme
import com.example.foodie.timer.AppTimer
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val restaurantsPageViewModel: RestaurantsPageViewModel by viewModels()

    @Inject
    lateinit var timer: AppTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        timer.start()
        setContent {
            FoodieTheme {
                MainView(restaurantsPageViewModel)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        timer.pause()
    }

    override fun onStart() {
        super.onStart()
        timer.start()
    }
}