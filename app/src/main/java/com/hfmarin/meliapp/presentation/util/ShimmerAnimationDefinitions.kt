package com.hfmarin.meliapp.presentation.util

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

class ShimmerAnimationDefinitions(
    internal val widthPx: Float,
    internal val heightPx: Float,
    internal val animationDuration: Int = 1300,
    internal val animationDelay: Int = 300
) {
    var gradientWidth: Float = (0.2f * heightPx)

    enum class AnimationsState { START, END }

    class TransitionData(
        xShimmerPropKey: State<Float>,
        yShimmerPropKey: State<Float>
    ) {
        val xShimmerPropKey by xShimmerPropKey
        val yShimmerPropKey by yShimmerPropKey
    }
}

@Composable
fun shimmerTransitionAnimation(
    shimmerAnimationDefinitions: ShimmerAnimationDefinitions
): ShimmerAnimationDefinitions.TransitionData {
    val transition = rememberInfiniteTransition()

    val transitionX = transition.animateFloat(
        initialValue = 0f,
        targetValue = shimmerAnimationDefinitions.widthPx + shimmerAnimationDefinitions.gradientWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = shimmerAnimationDefinitions.animationDuration,
                easing = LinearEasing,
                delayMillis = shimmerAnimationDefinitions.animationDelay
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    val transitionY = transition.animateFloat(
        initialValue = 0f,
        targetValue = shimmerAnimationDefinitions.heightPx + shimmerAnimationDefinitions.gradientWidth,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = shimmerAnimationDefinitions.animationDuration,
                easing = LinearEasing,
                delayMillis = shimmerAnimationDefinitions.animationDelay
            ),
            repeatMode = RepeatMode.Restart
        )
    )
    return remember(transition) {
        ShimmerAnimationDefinitions.TransitionData(transitionX, transitionY)
    }
}