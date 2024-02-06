package com.sahidev.maknyuss.feature.util

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

fun Modifier.shimmerEffect(durationMillis: Int = 1500): Modifier = composed {
    val shimmerColors = listOf(
        Color(0xFFCCCCCC),
        Color(0xFFD8D8D8),
        Color(0xFFCCCCCC),
    )

    val transition = rememberInfiniteTransition(label = "Shimmer effect")
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis), repeatMode = RepeatMode.Restart
        ),
        label = "Shimmer effect"
    )

    background(
        brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset(x = translateAnimation.value - 500f, y = translateAnimation.value - 500f),
            end = Offset(x = translateAnimation.value, y = translateAnimation.value)
        )
    )
}