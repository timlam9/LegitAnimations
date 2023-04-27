package com.lamti.legitanimations.goal

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WaveContent(modifier: Modifier = Modifier, onOkClick: () -> Unit) {
    var showContent by remember { mutableStateOf(false) }
    SideEffect { showContent = true }

    AnimatedVisibility(
        visible = showContent,
        enter = scaleIn() + fadeIn(initialAlpha = 0.3f),
        exit = scaleOut() + fadeOut()
    ) {
        Box(
            modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Goal Completed", color = Color.White)

                Button(
                    onClick = onOkClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error.copy(
                            alpha = 0.8f
                        )
                    ),
                ) {
                    Text("Got it!", color = Color.White)
                }
            }
        }
    }
}
