package com.example.foodie.pages.restaurantspage.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.foodie.R
import com.example.foodie.model.Restaurant
import com.example.foodie.model.VenueVerticalList
import com.example.foodie.pages.restaurantspage.RestaurantsPageViewModel
import com.example.foodie.pages.shared.ErrorRetryView
import com.example.foodie.pages.shared.RetryView
import com.example.foodie.pages.shared.SplashAppBar
import com.example.foodie.pages.utils.PreviewUtils
import com.example.foodie.theme.FoodieTheme
import com.example.foodie.utils.UiState
import com.example.foodie.utils.stringResources
import com.example.foodie.utils.toTextHolder

@Composable
fun MainView(restaurantsPageViewModel: RestaurantsPageViewModel) {
    MainView(
        uiState = restaurantsPageViewModel.state.value,
        showSplashScreenEnterAnimation = true,
        onFavoriteClick = restaurantsPageViewModel::onFavoriteClick,
        onRetryClick = restaurantsPageViewModel::onRetryClick,
    )
}

@Composable
fun MainView(
    uiState: UiState<VenueVerticalList>,
    showSplashScreenEnterAnimation: Boolean = false,
    onFavoriteClick: (Restaurant, Boolean) -> Unit = { _, _ -> },
    onRetryClick: () -> Unit = {},
) {
    Surface {
        Column {
            SplashAppBar(
                collapsed = uiState.initialized,
                showEnterAnimation = showSplashScreenEnterAnimation,
            )

            when {
                !uiState.initialized -> Unit
                uiState.errorMessage != null -> ErrorRetryView(
                    errorMessage = stringResources(uiState.errorMessage),
                    onRetryClick = onRetryClick,
                )
                uiState.successData != null -> VenueVerticalListView(
                    section = uiState.successData,
                    loading = uiState.loading,
                    onFavoriteClick = onFavoriteClick,
                )
                uiState.loading -> LoadingVenueVerticalListView()
                else -> RetryView(
                    message = stringResource(id = R.string.info_sorry_no_data_to_display),
                    subMessage = stringResource(id = R.string.info_try_to_refresh_later),
                    onRetryClick = onRetryClick,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ErrorPreview() {
    FoodieTheme {
        MainView(
            uiState = UiState(
                initialized = true,
                loading = false,
                errorMessage = "Database is unreachable".toTextHolder(),
            ),
        )
    }
}

@Preview
@Composable
private fun LoadingPreview() {
    FoodieTheme {
        MainView(
            uiState = UiState(
                initialized = true,
                loading = true,
            ),
        )
    }
}

@Preview
@Composable
private fun SuccessPreview() {
    FoodieTheme {
        MainView(
            uiState = UiState(
                initialized = true,
                loading = false,
                successData = PreviewUtils.venueList,
            ),
        )
    }
}

@Preview
@Composable
private fun NotInitializedPreview() {
    FoodieTheme {
        MainView(
            uiState = UiState(
                initialized = false,
                loading = false,
            ),
        )
    }
}

@Preview
@Composable
private fun NoDataPreview() {
    FoodieTheme {
        MainView(
            uiState = UiState(
                initialized = true,
                loading = false,
                successData = null,
            ),
        )
    }
}