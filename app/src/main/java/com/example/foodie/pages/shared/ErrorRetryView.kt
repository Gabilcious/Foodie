package com.example.foodie.pages.shared

import android.content.res.Configuration
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.foodie.R
import com.example.foodie.theme.FoodieTheme

@Composable
fun ErrorRetryView(
    modifier: Modifier = Modifier,
    errorMessage: String = "",
    onRetryClick: () -> Unit = {},
) {
    RetryView(
        message = stringResource(id = R.string.error_something_went_wrong_try_again_later),
        subMessage = errorMessage,
        modifier = modifier,
        onRetryClick = onRetryClick,
    )
}

@Preview(
    name = "Light Mode",
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode",
)
@Composable
private fun ErrorRetryPreview() {
    FoodieTheme {
        Surface {
            ErrorRetryView(
                errorMessage = "No Internet connection",
            )
        }
    }
}