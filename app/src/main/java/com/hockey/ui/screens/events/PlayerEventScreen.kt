package com.hockey.ui.screens.events

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.hockey.ui.theme.HockeyTheme

@Composable
fun PlayerEventScreen(modifier: Modifier = Modifier, navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Player Event Screen",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

    }
}

@Preview(showBackground = true)
@Composable
fun PlayerEventScreenPreview() {
    HockeyTheme {
        PlayerEventScreen(navController = NavController(LocalContext.current))
    }
}
