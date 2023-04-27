package com.lamti.legitanimations

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun Wave(
    modifier: Modifier = Modifier,
    animationDuration: Int = 2000
) {
    val deltaXAnim = rememberInfiniteTransition()
    val dx by deltaXAnim.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(500, easing = LinearEasing))
    )

    val screenWidthPx = with(LocalDensity.current) {
        (LocalConfiguration.current.screenHeightDp * density) - 150.dp.toPx()
    }
    var translate by remember { mutableStateOf(screenWidthPx) }
    val animTranslate by animateFloatAsState(
        targetValue = translate,
        animationSpec = TweenSpec(durationMillis = animationDuration, easing = LinearOutSlowInEasing),
    )

    var waveHeight by remember { mutableStateOf(100f) }
    val animHeight by animateFloatAsState(
        targetValue = waveHeight,
        animationSpec = TweenSpec(durationMillis = animationDuration, easing = LinearEasing)
    )

    val initColor = MaterialTheme.colorScheme.primary
    val targetColor = MaterialTheme.colorScheme.secondary
    var color by remember { mutableStateOf(initColor) }
    val animColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(durationMillis = animationDuration, easing = LinearEasing)
    )

    LaunchedEffect(key1 = Unit) {
        waveHeight = 0f
        translate = 0f
        color = targetColor
    }

    val waveWidth = 300
    val originalY = 150f
    val path = Path()

    Canvas(
        modifier = modifier.fillMaxSize(),
        onDraw = {
            translate(top = animTranslate) {
                drawPath(path = path, color = animColor)
                path.reset()
                val halfWaveWidth = waveWidth / 2
                path.moveTo(-waveWidth + (waveWidth * dx), originalY.dp.toPx())

                for (i in -waveWidth..(size.width.toInt() + waveWidth) step waveWidth) {
                    path.relativeQuadraticBezierTo(
                        dx1 = halfWaveWidth.toFloat() / 2,
                        dy1 = -animHeight,
                        dx2 = halfWaveWidth.toFloat(),
                        dy2 = 0f
                    )
                    path.relativeQuadraticBezierTo(
                        dx1 = halfWaveWidth.toFloat() / 2,
                        dy1 = animHeight,
                        dx2 = halfWaveWidth.toFloat(),
                        dy2 = 0f
                    )
                }

                path.lineTo(size.width, size.height)
                path.lineTo(0f, size.height)
                path.close()
            }
        }
    )
}
