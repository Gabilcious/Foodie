package com.example.foodie.pages.restaurantspage.view

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodie.R
import com.example.foodie.model.Restaurant
import com.example.foodie.model.VenueVerticalList
import com.example.foodie.pages.restaurantspage.view.components.RestaurantItemView
import com.example.foodie.pages.utils.PreviewUtils
import com.example.foodie.theme.FoodieTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VenueVerticalListView(
    section: VenueVerticalList,
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    onFavoriteClick: (Restaurant, Boolean) -> Unit = { _, _ -> },
) {
    Box(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .animateContentSize(),
        ) {
            item { Spacer(Modifier.height(40.dp)) }
            items(
                count = section.restaurants.size,
                key = { idx -> section.restaurants[idx].id.value },
            ) { idx ->
                RestaurantItemView(
                    restaurant = section.restaurants[idx],
                    modifier = remember {
                        Modifier
                            .animateItemPlacement()
                            .animateContentSize()
                    },
                    showDivider = idx < section.restaurants.lastIndex,
                    onFavoriteClick = remember {
                        { value ->
                            onFavoriteClick(
                                section.restaurants[idx],
                                value,
                            )
                        }
                    },
                )
            }
        }
        Row(
            modifier = Modifier
                .height(60.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colors.surface,
                            Color.Transparent,
                        ),
                        startY = 110f,
                    ),
                    alpha = 0.95f,
                ),
        ) {
            Text(
                text = section.title,
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.secondary,
                modifier = modifier
                    .weight(1.0f)
                    .padding(start = 16.dp, top = 8.dp),
            )
            if (loading) {
                Text(
                    text = stringResource(id = R.string.info_refreshing_data),
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier.padding(end = 16.dp, top = 8.dp),
                )
            }
        }
    }
}

@Preview(
    name = "Light Mode",
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode",
)
@Composable
private fun VenueVerticalListLoadingPreview() {
    FoodieTheme {
        Surface {
            VenueVerticalListView(
                section = PreviewUtils.venueList,
                loading = true,
            )
        }
    }
}