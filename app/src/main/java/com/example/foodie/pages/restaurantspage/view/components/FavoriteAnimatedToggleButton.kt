package com.example.foodie.pages.restaurantspage.view.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.foodie.R
import com.example.foodie.pages.utils.NoRippleInteractionSource
import com.example.foodie.theme.FoodieTheme
import kotlin.math.abs

@Composable
fun FavoriteAnimatedToggleButton(
    isFavorite: Boolean,
    modifier: Modifier = Modifier,
    onFavoriteClick: (Boolean) -> Unit = {},
) {
    val animationSpec: FiniteAnimationSpec<Float> = tween(
        durationMillis = 500,
        easing = EaseOut,
    )
    val scale = animateFloatAsState(
        targetValue = if (isFavorite) -0.5f else 0.5f,
        animationSpec = animationSpec,
    )

    IconToggleButton(
        checked = isFavorite,
        onCheckedChange = onFavoriteClick,
        interactionSource = NoRippleInteractionSource(),
        modifier = modifier
            .scale(1.5f - abs(scale.value)),
    ) {
        Crossfade(
            targetState = isFavorite,
            animationSpec = animationSpec,
        ) {
            if (it) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(
                        R.string.content_description_remove_from_favorites_button,
                    ),
                    tint = MaterialTheme.colors.secondary,
                )
            } else {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.content_description_add_to_favorites_button),
                    tint = MaterialTheme.colors.secondary,
                )
            }
        }
    }
}

@Preview
@Composable
private fun FavoriteAnimatedToggleButtonPreview() {
    FoodieTheme {
        Surface {
            FavoriteAnimatedToggleButton(true)
        }
    }
}