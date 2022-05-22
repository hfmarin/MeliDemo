package com.hfmarin.meliapp.presentation.components

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.hfmarin.meliapp.presentation.util.ShimmerAnimationDefinitions
import com.hfmarin.meliapp.presentation.util.shimmerTransitionAnimation

@Composable
fun LoadingItemViewShimmer(
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
            item {
                val brush = Brush.linearGradient(
                    colors,
                    start = Offset(
                        xCardShimmer - cardAnimationDefinition.widthPx,
                        yCardShimmer - cardAnimationDefinition.widthPx
                    ),
                    end = Offset(xCardShimmer, yCardShimmer)
                )
                Column(modifier = Modifier.padding(padding)) {
                    Surface(
                        shape = MaterialTheme.shapes.small,
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(imageHeight)
                                .background(brush = brush)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        shape = MaterialTheme.shapes.small,
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(imageHeight / 10)
                                .background(brush = brush)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = MaterialTheme.shapes.small,
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(imageHeight / 10)
                                .background(brush = brush)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = MaterialTheme.shapes.small,
                    ) {
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(imageHeight / 10)
                                .background(brush = brush)
                        )
                    }
                }
            }
        }
    }
}