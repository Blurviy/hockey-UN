package com.hockey

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController


@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val selectedNavigationIndex = rememberSaveable {
        mutableStateOf(0)
    }

    data class NavigationItem(
        val title: String,
        val icon: ImageVector,
        val route: String
    )

    val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Default.Home,
            route = Screen.Home.route
        ),
        NavigationItem(
            title = "Team",
            icon = Icons.Default.Star,
            route = Screen.TeamRegistration.route
        ),
        NavigationItem(
            title = "Players",
            icon = Icons.Default.Person,
            route = Screen.PlayerManagement.route
        ),
        NavigationItem(
            title = "Events",
            icon = Icons.Default.DateRange,
            route = Screen.Events.route
        ),
        NavigationItem(
            title = "News",
            icon = Icons.Default.Info,
            route = Screen.NewsAndUpdate.route
        ),
        NavigationItem(
            title = "Settings",
            icon = Icons.Default.Settings,
            route = Screen.Settings.route
        )
    )



    NavigationBar(containerColor = Color.Green) {
        navigationItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selectedNavigationIndex.value,
                onClick = {
                    selectedNavigationIndex.value = index
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = {
                    Text(
                        text = item.title,
                        color = if (index == selectedNavigationIndex.value) Color.Black else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    }

}

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object TeamRegistration : Screen("team_registration_screen")
    object PlayerManagement : Screen("player_management_screen")
    object PlayerRegistration : Screen("player_registration_screen") // Not needed for the navigation bar
    object Events : Screen("event_screen")
    object NewsAndUpdate : Screen("news_and_update_screen")
    object Settings : Screen("settings_screen") // Not needed for the navigation bar
    object Login : Screen("login_screen") // Not needed for the navigation bar
    object Signup : Screen("signup_screen") // Not needed for the navigation bar
}





