package com.hockey.navigation

import androidx.compose.foundation.layout.Box
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
import androidx.navigation.compose.rememberNavController
import com.hockey.R
import com.hockey.ui.screens.events.AdminEventScreen
import com.hockey.ui.screens.events.EventScreen
import com.hockey.ui.screens.events.FanEventScreen
import com.hockey.ui.screens.events.ManagerEventScreen
import com.hockey.ui.screens.events.PlayerEventScreen
import com.hockey.ui.screens.home.HomeScreen
import com.hockey.ui.screens.news.NewsAndUpdateScreen
import com.hockey.ui.screens.settings.Settings
import com.hockey.ui.screens.settings.SettingsScreen
import com.hockey.ui.screens.team.AdminTeamScreen
import com.hockey.ui.screens.team.PlayerManagementScreen
import com.hockey.ui.screens.team.TeamScreen

@Composable
fun NavigationBar(
    role: String,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    // Define role-based navigation items
    val navBarItemList = when (role) {
        "noLogin" -> listOf(
            NavBarItem("Home", { Icon(imageVector = Icons.Default.Home, contentDescription = "Home Icon") }, 0),
            NavBarItem("Events", { Icon(imageVector = Icons.Default.DateRange, contentDescription = "Events Icon") }, 0),
            NavBarItem("Updates", { Icon(imageVector = Icons.Default.Notifications, contentDescription = "Updates Icon") }, 0)
        )
        "manager" -> listOf(
            NavBarItem("Home", { Icon(imageVector = Icons.Default.Home, contentDescription = "Home Icon") }, 0),
            NavBarItem("Team", { Icon(painterResource(id = R.drawable.ic_groups), contentDescription = "Team Icon", Modifier.size(24.dp)) }, 0),
            NavBarItem("Events", { Icon(imageVector = Icons.Default.DateRange, contentDescription = "Events Icon") }, 0),
            NavBarItem("Updates", { Icon(imageVector = Icons.Default.Notifications, contentDescription = "Updates Icon") }, 0),
            NavBarItem("Settings", { Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings Icon") }, 0)
        )
        "fan" -> listOf(
            NavBarItem("Home", { Icon(imageVector = Icons.Default.Home, contentDescription = "Home Icon") }, 0),
            NavBarItem("Events", { Icon(imageVector = Icons.Default.DateRange, contentDescription = "Events Icon") }, 0),
            NavBarItem("Updates", { Icon(imageVector = Icons.Default.Notifications, contentDescription = "Updates Icon") }, 0),
            NavBarItem("Settings", { Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings Icon") }, 0)
        )
        "player" -> listOf(
            NavBarItem("Home", { Icon(imageVector = Icons.Default.Home, contentDescription = "Home Icon") }, 0),
            NavBarItem("Team", { Icon(painterResource(id = R.drawable.ic_groups), contentDescription = "Team Icon", Modifier.size(24.dp)) }, 0),
            NavBarItem("Events", { Icon(imageVector = Icons.Default.DateRange, contentDescription = "Events Icon") }, 0),
            NavBarItem("Updates", { Icon(imageVector = Icons.Default.Notifications, contentDescription = "Updates Icon") }, 0),
            NavBarItem("Settings", { Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings Icon") }, 0)
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

    // Track which tab is currently selected
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
        // Pass the same NavController into ContentScreen
        ContentScreen(
            modifier = Modifier.padding(innerPadding),
            selectedScreen = selectedScreen,
            role = role,
            navController = navController
        )
    }
}

@Composable
fun ContentScreen(
    modifier: Modifier = Modifier,
    selectedScreen: Int,
    role: String,
    navController: NavController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (role) {
            "noLogin" -> when (selectedScreen) {
                0 -> HomeScreen(navController, role = "noLogin")
                1 -> EventScreen(
                    role = "noLogin",
                    onEventClick = { event ->
                        // Navigate to the details route for this event
                        navController.navigate("event_details/${event.id}")
                    },
                    onRegisterTeamClick = {}, // No registration for noLogin
                    onAddEventClick = {},
                    navController = NavController(LocalContext.current)
                )

                2 -> NewsAndUpdateScreen(navController = NavController(LocalContext.current))
            }

            "manager" -> when (selectedScreen) {
                0 -> HomeScreen(navController, role = "manager")
                1 -> TeamScreen(navController)
                2 -> ManagerEventScreen(navController = NavController(LocalContext.current))
                3 -> NewsAndUpdateScreen(navController = NavController(LocalContext.current))
                4 -> Settings()
            }

            "fan" -> when (selectedScreen) {
                0 -> HomeScreen(navController, role = "fan")
                1 -> EventScreen(
                    role = "noLogin",
                    onEventClick = { event ->
                        // Navigate to the details route for this event
                        navController.navigate("event_details/${event.id}")
                    },
                    onRegisterTeamClick = {}, // No registration for noLogin
                    onAddEventClick = {},
                    navController = NavController(LocalContext.current)
                ) // navController.navigate("event_list_fan") // Placeholder for now as the function is yet to be implemented FanEventScreen(navController = NavController(LocalContext.current))
                2 -> NewsAndUpdateScreen(navController = NavController(LocalContext.current))
                3 -> Settings()
            }

            "player" -> when (selectedScreen) {
                0 -> HomeScreen(navController, role = "player")
                1 -> PlayerManagementScreen(
                    navController = rememberNavController(),
                    role = "player"
                )

                2 -> EventScreen(
                    role = "noLogin",
                    onEventClick = { event ->
                        // Navigate to the details route for this event
                        navController.navigate("event_details/${event.id}")
                    },
                    onRegisterTeamClick = {}, // No registration for noLogin
                    onAddEventClick = {},
                    navController = NavController(LocalContext.current)
                ) // PlayerEventScreen(navController = NavController(LocalContext.current))
                3 -> NewsAndUpdateScreen(navController = NavController(LocalContext.current))
                4 -> Settings()
            }

            "admin" -> when (selectedScreen) {
                0 -> HomeScreen(navController, role = "admin")
                1 -> AdminTeamScreen(navController = navController)
                2 -> AdminEventScreen(navController = NavController(LocalContext.current))
                3 -> NewsAndUpdateScreen(navController = NavController(LocalContext.current), role = "admin`````")
                4 -> Settings()
            }
        }
    }
}


// Data class representing a navigation bar item
data class NavBarItem(
    val label: String,
    val icon: @Composable () -> Unit,
    var badgeCount: Int
)

@Preview
@Composable
fun AdminNavigationPreview() {
    // You’ll need a dummy NavController here for preview, but previews won’t show navigation
    NavigationBar(role = "admin", navController = rememberNavController())
}

@Preview
@Composable
fun NoLoginNavigationPreview() {
    NavigationBar(role = "noLogin", navController = rememberNavController())
}

@Preview
@Composable
fun FanNavigationPreview() {
    NavigationBar(role = "fan", navController = rememberNavController())
}

@Preview
@Composable
fun ManagerNavigationPreview() {
    NavigationBar(role = "manager", navController = rememberNavController())
}

@Preview
@Composable
fun PlayerNavigationPreview() {
    NavigationBar(role = "player", navController = rememberNavController())
}
