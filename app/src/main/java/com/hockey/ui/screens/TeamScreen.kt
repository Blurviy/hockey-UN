package com.hockey.ui.screens

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
import com.hockey.ui.screens.PlayerManagementActivity
import com.hockey.ui.screens.PlayerRegistrationActivity
import com.hockey.ui.screens.TeamRegistrationActivity

@Composable
fun TeamManagementScreen(context: Context) {
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
            context.startActivity(Intent(context, PlayerManagementActivity::class.java))
        }
        ActivityCard("Player Registration") {
            context.startActivity(Intent(context, PlayerRegistrationActivity::class.java))
        }
        ActivityCard("Team Registration") {
            context.startActivity(Intent(context, TeamRegistrationActivity::class.java))
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
fun TeamManagementScreenPreview() {
    HockeyTheme {
        TeamManagementScreen(context = androidx.compose.ui.platform.LocalContext.current)
    }
}
