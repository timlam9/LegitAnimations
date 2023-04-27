package com.lamti.legitanimations.goal

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WaveScreen() {
    Box(
        modifier = Modifier.fillMaxSize().padding(54.dp),
        contentAlignment = Alignment.Center
    ) {
        var playProgress by remember { mutableStateOf(false) }
        var isAcknowledge by remember { mutableStateOf(false) }

        val progress by animateFloatAsState(
            targetValue = if (playProgress) 1f else 0f,
            animationSpec = tween(1000)
        )
        val goalState = rememberGoalCardState(
            progress = progress,
            isAcknowledged = isAcknowledge
        )

        Button(
            onClick = { playProgress = !playProgress },
            modifier = Modifier.padding(bottom = 20.dp).align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
        ) {
            Text(if (playProgress) "hide" else "show")
        }

        GoalCard(
            state = goalState,
            content = { Text("Goal on 60%") },
            completeContent = {
                WaveContent {
                    isAcknowledge = !isAcknowledge
                }
            }
        )
    }
}
