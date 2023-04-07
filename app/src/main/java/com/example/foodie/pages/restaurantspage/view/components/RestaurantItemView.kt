package com.example.foodie.pages.restaurantspage.view.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodie.model.Restaurant
import com.example.foodie.pages.utils.PreviewUtils
import com.example.foodie.theme.FoodieTheme

@Composable
fun RestaurantItemView(
    restaurant: Restaurant,
    modifier: Modifier = Modifier,
    showDivider: Boolean = false,
    onFavoriteClick: (Boolean) -> Unit = {},
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(all = 8.dp),
        ) {
            Card(
                modifier = Modifier
                    .shadow(4.dp)
                    .size(48.dp),
            ) {
                RestaurantItemImage(
                    model = restaurant.imageUrl,
                    contentDescription = restaurant.name,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(modifier = Modifier.weight(1.0f)) {
                Text(
                    text = restaurant.name,
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.secondary,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = restaurant.shortDescription,
                    style = MaterialTheme.typography.body2,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            FavoriteAnimatedToggleButton(
                isFavorite = restaurant.isFavorite,
                onFavoriteClick = onFavoriteClick,
            )
        }
        if (showDivider) {
            Divider(
                thickness = 1.dp,
                modifier = Modifier
                    .padding(
                        end = 8.dp,
                        start = 64.dp,
                    ),
            )
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
private fun RestaurantItemPreview() {
    FoodieTheme {
        Surface {
            Column {
                RestaurantItemView(
                    restaurant = PreviewUtils.restaurant,
                    showDivider = true,
                )
                RestaurantItemView(
                    restaurant = PreviewUtils.restaurant.copy(isFavorite = true),
                    showDivider = true,
                )
                RestaurantItemView(
                    restaurant = PreviewUtils.restaurant.copy(
                        shortDescription = "Amazing place to eat during the day. " +
                                "You can eat as much as you want. We deliver to you " +
                                "even in the middle of the night",
                    ),
                    showDivider = false,
                )
            }
        }
    }
}