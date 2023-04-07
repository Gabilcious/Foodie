package com.example.foodie.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource

sealed interface TextHolder

class StringHolder(val value: String) : TextHolder

class ResourceHolder(val resourceId: Int) : TextHolder

@Composable
@ReadOnlyComposable
fun stringResources(textHolder: TextHolder): String =
    when (textHolder) {
        is StringHolder -> textHolder.value
        is ResourceHolder -> stringResource(id = textHolder.resourceId)
    }

fun String.toTextHolder() = StringHolder(this)