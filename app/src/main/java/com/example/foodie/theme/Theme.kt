package com.example.foodie.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = BlueGray200,
    primaryVariant = BlueGray500,
    secondary = BlueGray200,
)

private val LightColorPalette = lightColors(
    primary = BlueGray500,
    primaryVariant = BlueGray700,
    secondary = BlueGray900,
)

@Composable
fun FoodieTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content,
    )
}