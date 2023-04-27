package com.lamti.legitanimations.goal

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

data class GoalState(
    val progress: Float,
    val isAcknowledged: Boolean = false,
    val onGoalCompleted: () -> Unit = {},
    val onWaveAnimationCompleted: () -> Unit = {},
)

@Composable
fun rememberGoalCardState(
    isAcknowledged: Boolean = false,
    progress: Float = 0f,
    onGoalCompleted: () -> Unit = {},
    onAnimationCompleted: () -> Unit = {},
): GoalState {
    return remember(isAcknowledged, progress) {
        GoalState(
            progress = progress,
            isAcknowledged = isAcknowledged,
            onGoalCompleted = onGoalCompleted,
            onWaveAnimationCompleted = onAnimationCompleted
        )
    }
}
