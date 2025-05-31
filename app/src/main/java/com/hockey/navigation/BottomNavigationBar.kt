package com.hockey.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hockey.R
import com.hockey.ui.screens.events.EventScreen
import com.hockey.ui.screens.home.HomeScreen
import com.hockey.ui.screens.news.NewsAndUpdateScreen
import com.hockey.ui.screens.settings.SettingsScreen
import com.hockey.ui.screens.team.TeamManagementScreen

@Composable
fun NavigationBar(role: String, modifier: Modifier = Modifier) {
    // Define role-based navigation items
    val navBarItemList = when (role) {
        "noLogin" -> listOf(
            NavBarItem("Home", { Icon(imageVector = Icons.Default.Home, contentDescription = "Home Icon") }, 0),
            NavBarItem("Updates", { Icon(imageVector = Icons.Default.Notifications, contentDescription = "Updates Icon") }, 0),
        )
        "manager" -> listOf(
            NavBarItem("Home", { Icon(imageVector = Icons.Default.Home, contentDescription = "Home Icon") }, 0),
            NavBarItem("Team", { Icon(painterResource(id = R.drawable.ic_groups), contentDescription = "Team Icon", Modifier.size(24.dp)) }, 0),
            NavBarItem("Events", { Icon(imageVector = Icons.Default.DateRange, contentDescription = "Events Icon") }, 0),
            NavBarItem("Settings", { Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings Icon") }, 0)
        )
        "fan" -> listOf(
            NavBarItem("Home", { Icon(imageVector = Icons.Default.Home, contentDescription = "Home Icon") }, 0),
            NavBarItem("Events", { Icon(imageVector = Icons.Default.DateRange, contentDescription = "Events Icon") }, 0),
            NavBarItem("Updates", { Icon(imageVector = Icons.Default.Notifications, contentDescription = "Updates Icon") }, 0)
        )
        "player" -> listOf(
            NavBarItem("Home", { Icon(imageVector = Icons.Default.Home, contentDescription = "Home Icon") }, 0),
            NavBarItem("Team", { Icon(painterResource(id = R.drawable.ic_groups), contentDescription = "Team Icon", Modifier.size(24.dp)) }, 0),
            NavBarItem("Events", { Icon(imageVector = Icons.Default.DateRange, contentDescription = "Events Icon") }, 0)
        )
        "admin" -> listOf(
            NavBarItem("Home", { Icon(imageVector = Icons.Default.Home, contentDescription = "Home Icon") }, 0),
            NavBarItem("Team", { Icon(painterResource(id = R.drawable.ic_groups), contentDescription = "Team Icon", Modifier.size(24.dp)) }, 0),
            NavBarItem("Events", { Icon(imageVector = Icons.Default.DateRange, contentDescription = "Events Icon") }, 0),
            NavBarItem("Updates", { Icon(imageVector = Icons.Default.Notifications, contentDescription = "Updates Icon") }, 0),
            NavBarItem("Settings", { Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings Icon") }, 0)
        )
        else -> emptyList() // Handle unknown roles
    }

    // State to track the currently selected screen
    var selectedScreen by remember { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 5.dp),
        bottomBar = {
            NavigationBar {
                navBarItemList.forEachIndexed { index, navBarItem ->
                    NavigationBarItem(
                        selected = selectedScreen == index,
                        onClick = { selectedScreen = index },
                        icon = {
                            BadgedBox(badge = {
                                if (navBarItem.badgeCount > 0)
                                    Badge { Text(text = navBarItem.badgeCount.toString()) }
                            }) {
                                navBarItem.icon()
                            }
                        },
                        label = { Text(navBarItem.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        ContentScreen(modifier = Modifier.padding(innerPadding), selectedScreen, role)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedScreen: Int, role: String) {
    when (role) {
        "noLogin" -> when (selectedScreen) {
            0 -> HomeScreen()
            1 -> NewsAndUpdateScreen()
        }
        "manager" -> when (selectedScreen) {
            0 -> HomeScreen()
            1 -> TeamManagementScreen(navController = NavController(LocalContext.current))
            2 -> EventScreen()
            3 -> SettingsScreen()
        }
        "fan" -> when (selectedScreen) {
            0 -> HomeScreen()
            1 -> EventScreen()
            2 -> NewsAndUpdateScreen()
        }
        "player" -> when (selectedScreen) {
            0 -> HomeScreen()
            1 -> TeamManagementScreen(navController = NavController(LocalContext.current))
            2 -> EventScreen()
        }
        "admin" -> when (selectedScreen) {
            0 -> HomeScreen()
            1 -> TeamManagementScreen(navController = NavController(LocalContext.current))
            2 -> EventScreen()
            3 -> NewsAndUpdateScreen()
            4 -> SettingsScreen()
        }
    }
}

// Data class representing a navigation bar item
data class NavBarItem (
    val label: String,
    val icon: @Composable () -> Unit, // Icon representing the item
    var badgeCount: Int, // Badge count for notifications
    )

@Preview
@Composable
fun AdminNavigationPreview() {
    NavigationBar(role = "admin")
}


@Preview
@Composable
fun NoLoginNavigationPreview(){
    NavigationBar(role = "noLogin")
}

@Preview
@Composable
fun FanNavigationPreview(){
    NavigationBar(role = "fan")
}

@Preview
@Composable
fun ManagerNavigationPreview(){
    NavigationBar(role = "manager")
}

@Preview
@Composable
fun PlayerNavigationPreview(){
    NavigationBar(role = "player")
}
