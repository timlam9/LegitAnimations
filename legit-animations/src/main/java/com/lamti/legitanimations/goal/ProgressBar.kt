package com.lamti.legitanimations.goal

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BoxScope.ProgressBar(
    modifier: Modifier = Modifier,
    height: Dp = 6.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f),
    progress: Float,
    topStartCorner: Dp = 0.dp,
    topEndCorner: Dp = 0.dp,
    bottomStartCorner: Dp = 20.dp,
    bottomEndCorner: Dp = 20.dp,
    onGoalComplete: () -> Unit
) {
    LaunchedEffect(progress) { if (progress == 1f) onGoalComplete() }
    LinearProgressIndicator(
        progress = progress,
        color = color,
        trackColor = backgroundColor,
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .clip(
                RoundedCornerShape(
                    topStart = topStartCorner,
                    topEnd = topEndCorner,
                    bottomStart = bottomStartCorner,
                    bottomEnd = bottomEndCorner
                )
            )
    )
}
