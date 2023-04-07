package com.example.foodie.pages.shared

import android.content.res.Configuration
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.foodie.R
import com.example.foodie.pages.utils.NoRippleInteractionSource
import com.example.foodie.theme.FoodieTheme

@Composable
fun RetryView(
    modifier: Modifier = Modifier,
    message: String = "",
    subMessage: String = "",
    onRetryClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var visible by remember { mutableStateOf(false) }
        var pressed by remember { mutableStateOf(0) }

        LaunchedEffect(key1 = true) {
            visible = true
        }
        Box(
            contentAlignment = Alignment.Center,
        ) {

            val spinWhenVisible by animateFloatAsState(
                targetValue = if (visible) 360f else 0f,
                animationSpec = tween(
                    durationMillis = 1_000,
                    delayMillis = 2_000,
                    easing = EaseInOutBack,
                ),
            )
            val spinWhenPressed by animateFloatAsState(
                targetValue = pressed * 360f,
                animationSpec = tween(
                    durationMillis = 1_000,
                    easing = EaseInOutBack,
                ),
            )

            val rotation by animateFloatAsState(
                targetValue = if (visible) 70f else 0f,
                animationSpec = tween(
                    durationMillis = 2_000,
                    easing = EaseOut,
                ),
            )

            val yOffset by animateDpAsState(
                targetValue = if (visible) 40.dp else 0.dp,
                animationSpec = tween(
                    durationMillis = 2_000,
                    easing = EaseOutBounce,
                ),
            )

            val xOffset by animateDpAsState(
                targetValue = if (visible) 210.dp else 0.dp,
                animationSpec = tween(
                    durationMillis = 2_000,
                    easing = EaseOutBounce,
                ),
            )

            Icon(
                painter = painterResource(R.drawable.foodie_fork),
                contentDescription = stringResource(id = R.string.content_description_foodie_logo),
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .rotate(degrees = rotation)
                    .offset(x = xOffset, y = yOffset),
            )
            IconButton(
                onClick = {
                    pressed++
                    onRetryClick()
                },
                modifier = Modifier.rotate(degrees = spinWhenVisible + spinWhenPressed),
                interactionSource = NoRippleInteractionSource(),
            ) {
                Crossfade(
                    targetState = visible,
                    animationSpec = tween(
                        durationMillis = 1_000,
                        delayMillis = 2_000,
                        easing = EaseInOut,
                    ),
                ) {
                    if (it) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(R.string.content_description_retry_button),
                            tint = MaterialTheme.colors.secondary,
                            modifier = Modifier.size(186.dp),
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.foodie_plate),
                            contentDescription = stringResource(R.string.content_description_retry_button),
                            tint = MaterialTheme.colors.secondary,
                            modifier = Modifier.size(186.dp),
                        )
                    }
                }
            }
            Icon(
                painter = painterResource(R.drawable.foodie_knife),
                contentDescription = stringResource(id = R.string.content_description_foodie_logo),
                tint = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .rotate(degrees = -rotation)
                    .offset(x = -xOffset, y = yOffset),
            )
        }
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(vertical = 8.dp),
        )
        Text(
            text = subMessage,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
        )
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
private fun RetryPreview() {
    FoodieTheme {
        Surface {
            RetryView(
                message = "There is no data to display",
                subMessage = "Try again later",
            )
        }
    }
}