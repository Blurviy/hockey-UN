package com.hockey.ui.screens.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hockey.ui.screens.events.EventScreen
import com.hockey.ui.theme.HockeyTheme

// Data classes to represent QuickStats and Activities
data class QuickStat(val title: String, val value: String, val icon: ImageVector, val onClick: () -> Unit)
data class Activity(val title: String, val description: String, val time: String) // Activity updates

@Composable
fun HomeScreen(modifier: Modifier = Modifier){
    // State to track the current screen to display
    var selectedScreen by remember { mutableStateOf("home") }

    // Back Button handler
    BackHandler(enabled = selectedScreen != "home") {
        selectedScreen = "home" // Navigate back to the home Screen
    }

    val quickStats = listOf(
        QuickStat(
            title = "Active Teams",
            value = "12",
            icon = Icons.Outlined.People,
            onClick = { selectedScreen = "active_teams" }
        ),
        QuickStat(
            title = "Upcoming Events",
            value = "5",
            icon = Icons.Outlined.Schedule,
            onClick = { selectedScreen = "events" }
        )
    )

    val activities = listOf(
        Activity(
            title = "New Player Added",
            description = "Mike Johnson joined Team Eagles",
            time = "2 hours ago"
        ),
        Activity(
            title = "Tournament Update",
            description = "Spring League 2025 registration open",
            time = "5 hours ago"
        )
    )

    // Display different screens based on selectedScreen state
    when (selectedScreen) {
        "home" -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Welcome Section
                Text(
                    text = "Welcome Back!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Manage your sports events and teams efficiently.",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                // Quick Stats Section
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    quickStats.forEach { stat ->
                        QuickStatsCard(
                            title = stat.title,
                            value = stat.value,
                            icon = stat.icon,
                            onClick = stat.onClick
                        )
                    }
                }

                // Recent Activities Section
                Text(
                    text = "Recent Activities",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                activities.forEach { activity ->
                    ActivityCard(
                        title = activity.title,
                        description = activity.description,
                        time = activity.time
                    )
                }
            }
        }
        "events" -> {
            EventScreen() // Show the EventScreen when "events" is selected
        }
        "active_teams" -> {
            ActiveTeamsScreen(teams = mockTeams)
        }
    }
}

@Composable
fun QuickStatsCard(
    title: String,
    value: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2D3748),
            contentColor = Color.White
        ),
        modifier = Modifier
            .height(100.dp)
            .width(180.dp)
            .clickable(onClick = onClick) // Adds clickable behaviour
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(icon, contentDescription = title, modifier = Modifier.size(24.dp))
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(title, fontSize = 14.sp)
        }
    }
}

@Composable
fun ActivityCard(title: String, description: String, time: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(description, fontSize = 14.sp, color = Color.Gray)
            Text(time, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HockeyTheme {
        HomeScreen()
    }
}