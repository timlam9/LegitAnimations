package com.lamti.legitanimations.goal

import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GoalCard(
    state: GoalState,
    modifier: Modifier = Modifier,
    height: Dp = 72.dp,
    shape: Shape = RoundedCornerShape(size = 8.dp),
    onCardClick: () -> Unit = {},
    content: @Composable RowScope.() -> Unit,
    completeContent: @Composable () -> Unit,
) {
    var showWave by remember { mutableStateOf(false) }
    var isStart by remember { mutableStateOf(true) }
    var showCompleteContent by remember { mutableStateOf(false) }

    LaunchedEffect(state.isAcknowledged) {
        if (!isStart) {
            showWave = !showWave
            showCompleteContent = !showCompleteContent
        }
        if (isStart) isStart = false
    }

    Box(modifier = modifier.height(height).fillMaxWidth().clip(shape)) {
        CustomCard(onClick = onCardClick, content = content)
        Wave(hide = !showWave, onComplete = {
            if (showWave) showCompleteContent = true
            state.onWaveAnimationCompleted()
        }) {
            AnimatedVisibility(
                visible = showCompleteContent,
                enter = scaleIn() + fadeIn(initialAlpha = 0.3f),
                exit = scaleOut() + fadeOut()
            ) {
                completeContent()
            }
        }
        ProgressBar(
            progress = state.progress,
            onGoalComplete = {
                showWave = true
                state.onGoalCompleted()
            }
        )
    }
}
