package com.hockey.ui.screens.news

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
import com.hockey.ui.viewmodels.AuthViewModel
import com.hockey.ui.theme.HockeyTheme

@Composable
fun NewsCreationScreen(modifier: Modifier = Modifier, navController: NavController) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "News Creation Screen to be done",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
fun NewsCreationScreenPreview(){
    HockeyTheme {
        NewsCreationScreen(navController = NavController(LocalContext.current))
    }
}