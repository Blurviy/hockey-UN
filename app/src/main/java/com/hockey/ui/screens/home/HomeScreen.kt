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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hockey.R
import com.hockey.navigation.AppScreen
import com.hockey.ui.screens.team.ActiveTeamsScreen
import com.hockey.ui.screens.team.mockTeams
import com.hockey.ui.theme.HockeyTheme
import com.hockey.utils.AppDropDown

// Data classes to represent QuickStats and Activities
data class QuickStat(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val onClickRoute: String
)

data class Activity(val title: String, val description: String, val time: String)

@Composable
fun HomeScreen(navController: NavController, modifier: Modifier = Modifier) {
    // Handle system Back: if this is not the root in NavHost, NavController will pop. No local state needed.
    BackHandler {
        if (!navController.popBackStack()) {
            // If there is nothing to pop, optionally finish the activity or do nothing.
        }
    }

    // Define QuickStats cards: navigate to event list or team management
    val quickStats = listOf(
        QuickStat(
            title = "Active Teams",
            value = "12",
            icon = Icons.Outlined.People,
            onClickRoute = "team_management" // This should match your NavHost route for TeamManagementScreen
        ),
        QuickStat(
            title = "Upcoming Events",
            value = "5",
            icon = Icons.Outlined.Schedule,
            onClickRoute = "event_list" // This should match your NavHost route for EventScreen
        )
    )

    // Sample Recent Activities (static for now)
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Welcome Section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 18.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
            text = "Welcome Back!",
            fontSize = 24.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )

            AppDropDown(
                menuItems = listOf(
                    Triple("Login", painterResource(id = R.drawable.ic_login)) {
                        navController.navigate(AppScreen.Login.route)
                    },
                    Triple("Signup", painterResource(id = R.drawable.ic_person_add)) {
                        navController.navigate(AppScreen.Signup.route)
                    }
                )
            )
        }
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
                    onClick = { navController.navigate(stat.onClickRoute) }
                )
            }
        }

        // Recent Activities Section
        Text(
            text = "Recent Activities",
            fontSize = 18.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
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
            .width(180.dp)
            .height(100.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(icon, contentDescription = title, modifier = Modifier.size(24.dp))
            Text(value, fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
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
            Text(title, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, fontSize = 16.sp)
            Text(description, fontSize = 14.sp, color = Color.Gray)
            Text(time, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HockeyTheme {
        // Provide a dummy NavController for preview
        HomeScreen(navController = NavController(LocalContext.current))
    }
}
