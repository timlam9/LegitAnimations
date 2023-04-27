package com.lamti.legitanimations.goal

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun Wave(
    modifier: Modifier = Modifier,
    animationDuration: Int = 2000,
    hide: Boolean = false,
    startColor: Color = MaterialTheme.colorScheme.primary,
    targetColor: Color = MaterialTheme.colorScheme.primary,
    onComplete: () -> Unit,
    content: @Composable () -> Unit,
) {
    val screenWidthPx = with(LocalDensity.current) { 172 * density }

    val waveWidth = 400
    val originalY = 0f
    val path = Path()

    val deltaXAnim = rememberInfiniteTransition()
    var translate by remember(hide) { mutableStateOf(screenWidthPx) }
    var waveHeight by remember(hide) { mutableStateOf(100f) }
    var color by remember(hide) { mutableStateOf(startColor) }
    var animationFinish by remember(hide) { mutableStateOf(false) }

    val dx by deltaXAnim.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(300, easing = LinearEasing))
    )

    val animTranslate by animateFloatAsState(
        targetValue = translate,
        animationSpec = TweenSpec(durationMillis = animationDuration, easing = LinearOutSlowInEasing)
    )
    val animHeight by animateFloatAsState(
        targetValue = waveHeight,
        animationSpec = TweenSpec(durationMillis = animationDuration, easing = LinearEasing),
        finishedListener = {
            animationFinish = true
            onComplete()
        }
    )
    val animColor by animateColorAsState(
        targetValue = color,
        animationSpec = TweenSpec(durationMillis = animationDuration, easing = LinearEasing),
    )

    fun hide() {
        animationFinish = false
        println("Hide")
        waveHeight = 100f
        translate = screenWidthPx
        color = startColor
    }

    fun show() {
        println("Show")
        translate = 0f
        waveHeight = 0f
        color = targetColor
    }

    LaunchedEffect(hide) { if (hide) hide() else show() }

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
    content()
}
