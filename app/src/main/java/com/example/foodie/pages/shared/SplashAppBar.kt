package com.example.foodie.pages.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.EaseOutCirc
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import androidx.constraintlayout.compose.layoutId
import com.example.foodie.R
import com.example.foodie.theme.BlueGray500
import com.example.foodie.theme.FoodieTheme

@OptIn(ExperimentalMotionApi::class)
@Composable
fun SplashAppBar(
    collapsed: Boolean,
    showEnterAnimation: Boolean = false,
) {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.splash_app_bar_motion_scene).readBytes()
            .decodeToString()
    }

    val progress by animateFloatAsState(
        targetValue = if (collapsed) 1f else 0f,
        tween(2_000),
    )
    val motionHeight by animateDpAsState(
        targetValue = if (collapsed) 56.dp else LocalConfiguration.current.screenHeightDp.dp,
        tween(2_000),
    )
    val fontSize by animateIntAsState(
        targetValue = if (collapsed) 20 else 96,
        tween(2_000),
    )

    var visible by remember { mutableStateOf(!showEnterAnimation) }
    val density = LocalDensity.current
    LaunchedEffect(key1 = true) {
        visible = true
    }

    MotionLayout(
        motionScene = MotionScene(content = motionScene),
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .height(motionHeight)
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        BlueGray500,
                    ),
                ),
            ),
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(
                animationSpec = tween(
                    durationMillis = 1_000,
                    easing = EaseOutCirc,
                ),
            ) {
                with(density) { 200.dp.roundToPx() }
            } + fadeIn(initialAlpha = 0.3f),
            modifier = Modifier.layoutId("title"),
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = if (progress > 0.5f) MaterialTheme.typography.h6 else MaterialTheme.typography.h1,
                fontSize = fontSize.sp,
                color = Color.White,
            )
        }
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(
                animationSpec = tween(
                    durationMillis = 2_000,
                    easing = EaseOutBounce,
                ),
            ) {
                with(density) { -500.dp.roundToPx() }
            } + fadeIn(initialAlpha = 0.3f),
            modifier = Modifier.layoutId("logo"),
        ) {
            Image(
                painter = painterResource(R.drawable.foodie_logo),
                contentDescription = stringResource(id = R.string.content_description_foodie_logo),
            )
        }
    }
}

@Preview
@Composable
private fun AppBarPreview() {
    FoodieTheme {
        Surface {
            SplashAppBar(true)
        }
    }
}

@Preview
@Composable
private fun SplashScreenPreview() {
    FoodieTheme {
        Surface {
            SplashAppBar(false)
        }
    }
}