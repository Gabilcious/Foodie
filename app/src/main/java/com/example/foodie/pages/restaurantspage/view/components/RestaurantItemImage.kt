package com.example.foodie.pages.restaurantspage.view.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.foodie.R
import com.example.foodie.theme.FoodieTheme

@OptIn(ExperimentalCoilApi::class)
@Composable
fun RestaurantItemImage(
    model: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
) {
    val painter = rememberImagePainter(
        data = model,
        builder = {
            placeholder(R.drawable.ic_grey_placeholder)
            error(R.drawable.restaurant_placeholder)
        },
    )

    Image(
        painter = painter,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun RestaurantItemImagePreview() {
    FoodieTheme {
        RestaurantItemImage(
            model = "https://preview",
            contentDescription = null,
        )
    }
}