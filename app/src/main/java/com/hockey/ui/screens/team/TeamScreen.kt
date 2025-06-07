package com.hockey.ui.screens.team

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hockey.ui.theme.HockeyTheme
import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController

@Composable
fun TeamScreen(modifier: Modifier = Modifier, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Screen title
        Text(
            text = "Team",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Cards for navigation
        ActivityCard("Player Management") {
            navController.navigate("player_management")
        }
        ActivityCard("Player Registration") {
            navController.navigate("player_registration")
        }
        ActivityCard("Team Registration") {
            navController.navigate("team_registration")
        }
    }
}

@Composable
fun ActivityCard(title: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Click for more details",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TeamScreenPreview() {
    HockeyTheme {
        TeamScreen(navController = NavController(LocalContext.current))
    }
}
