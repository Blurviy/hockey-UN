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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.hockey.ui.screens.news.News
import com.hockey.ui.screens.news.NewsCard
import com.hockey.ui.screens.news.newsList
import com.hockey.ui.screens.team.ActiveTeamsScreen
import com.hockey.ui.screens.team.mockTeams
import com.hockey.ui.theme.HockeyTheme
import com.hockey.utils.AppDropDown

// Data classes to represent QuickStats and Activities
data class QuickStat(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val onClickRoute: String?
)

data class Activity(val title: String, val description: String, val time: String)

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    role: String
) {
    // State to track the current screen to display
    var selectedScreen by remember { mutableStateOf("home") }

    var showWebView by remember { mutableStateOf(false) }
    var currentUrl by remember { mutableStateOf("") }
    var selectedNews by remember { mutableStateOf<News?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    // Back Button handler
    BackHandler(enabled = selectedScreen != "home") {
        selectedScreen = "home" // Navigate back to the home Screen
    }

    // Define QuickStats cards: navigate to event list or team management
    val quickStats = listOf(
        QuickStat(
            title = "Active Teams",
            value = "12",
            icon = Icons.Outlined.People,
            onClickRoute = "active_teams"
        ),
        QuickStat(
            title = "Upcoming Events",
            value = "5",
            icon = Icons.Outlined.Schedule,
            onClickRoute = null
        )
    )

    // Static Data for Cards
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
                    onClick = {
                        if (stat.title == "Upcoming Events") {
                            // Role-based navigation for Upcoming Events card
                            when (role) {
                                "noLogin" -> navController.navigate("event_list_noLogin")
                                "manager" -> navController.navigate("event_list_manager")
                                "player" -> navController.navigate("event_list_player")
                                "admin" -> navController.navigate("event_list_admin")
                                "fan" -> navController.navigate("event_list_fan")
                                else -> {/* DO NOTHING */}
                            }
                        } else {
                            // Static navigation for other cards
                            stat.onClickRoute?.let { route -> navController.navigate(route) }
                        }
                    }
                )
            }
        }

        // Recent Activities Section
        Text(
            text = "Recent Activities",
            fontSize = 18.sp,
            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp) // Add padding to separate from the header
        ) {
            items(newsList) { news ->
                NewsCard(news) {
                    if (news.link != null) {
                        currentUrl = news.link
                        showWebView = true
                    } else {
                        selectedNews = news
                        showDialog = true
                    }
                }
            }
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
        HomeScreen(navController = NavController(LocalContext.current), role = "manager")
    }
}
