package com.hfmarin.meliapp.presentation.components

import android.content.res.Resources
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hfmarin.meliapp.presentation.util.ShimmerAnimationDefinitions
import com.hfmarin.meliapp.presentation.util.shimmerTransitionAnimation

@Composable
fun LoadingItemListShimmer(
    imageHeight: Dp,
    padding: Dp = 16.dp
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val cardWidthPx = (maxWidth - (padding * 2))
        val cardHeightPx = (imageHeight - padding)

        val cardAnimationDefinition = remember {
            ShimmerAnimationDefinitions(
                widthPx = (Resources.getSystem().displayMetrics.density) * cardWidthPx.value,
                heightPx = (Resources.getSystem().displayMetrics.density) * cardHeightPx.value,
            )
        }
        val colors = listOf(
            Color.LightGray.copy(alpha = .9f),
            Color.LightGray.copy(alpha = .3f),
            Color.LightGray.copy(alpha = .9f),
        )

        val transition = shimmerTransitionAnimation(cardAnimationDefinition)
        val xCardShimmer = transition.xShimmerPropKey
        val yCardShimmer = transition.yShimmerPropKey

        LazyColumn() {
            items(5, itemContent = {
                ShimmerItemCardItem(
                    colors = colors,
                    xShimmer = xCardShimmer,
                    yShimmer = yCardShimmer,
                    cardHeight = imageHeight,
                    gradientWidth = cardAnimationDefinition.gradientWidth,
                    padding = padding
                )
            })

        }

    }
}