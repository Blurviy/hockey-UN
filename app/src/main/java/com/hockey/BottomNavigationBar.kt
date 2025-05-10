package com.hockey

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.hockey.ui.screens.EventScreen
import com.hockey.ui.screens.HomeScreen
import com.hockey.ui.screens.NewsAndUpdateScreen
import com.hockey.ui.screens.PlayerManagementScreen
import com.hockey.ui.screens.SettingsScreen
import com.hockey.ui.screens.TeamRegistrationScreen

@Composable
fun MainScreen(modifier: Modifier= Modifier) {

    // List of navigation bar items with labels, icons, and badge counts
    val navBarItemList = listOf(
        NavBarItem(
            label = "Home",
            icon = Icons.Default.Home,
            badgeCount = 0
            // route = Screen.Home.route
        ),
        NavBarItem(
            label = "Team",
            icon = Icons.Default.Star,
            badgeCount = 0
            // route = Screen.TeamRegistration.route
        ),
        /*NavBarItem(
            label = "Players",
            icon = Icons.Default.Person,
            badgeCount = 0
            // route = Screen.PlayerManagement.route
        ),*/
        NavBarItem(
            label = "Events",
            icon = Icons.Default.DateRange,
            badgeCount = 0
            // route = Screen.Events.route
        ),
        NavBarItem(
            label = "Notifications",
            icon = Icons.Default.Notifications,
            badgeCount = 0
            // route = Screen.NewsAndUpdate.route
        ),
        NavBarItem(
            label = "Settings",
            icon = Icons.Default.Settings,
            badgeCount = 0
            // route = Screen.Settings.route
        )
    )

    // State to track the currently selected screen
    var selectedScreen by remember { mutableIntStateOf(0) }

    // Scaffold provides the basic layout structure with a bottom navigation bar
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                // Dynamically create navigation items
                navBarItemList.forEachIndexed { index, navBarItem ->
                    NavigationBarItem(
                        selected =  selectedScreen == index,
                        onClick = { selectedScreen = index},
                        icon = {
                            // Add badges if applicable
                            BadgedBox(badge = {
                                if(navBarItem.badgeCount>0)
                                    Badge {
                                        Text(text = navBarItem.badgeCount.toString())
                                    }
                            }) {
                                Icon(imageVector = navBarItem.icon, contentDescription = "Icon") }
                            },
                        label = {
                            Text(navBarItem.label)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        // Render the content of the selected screen
        ContentScreen(modifier = Modifier.padding(innerPadding), selectedScreen)
    }
}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedScreen: Int) {
    // Display the content of the selected screen using a `when` block
    when (selectedScreen) {
        0 -> HomeScreen()
        1 -> TeamRegistrationScreen()
        //2 -> PlayerManagementScreen()
        2 -> EventScreen()
        3 -> NewsAndUpdateScreen()
        4-> SettingsScreen()
    }

}

// Data class representing a navigation bar item
data class NavBarItem (
    val label: String,
    val icon: ImageVector, // Icon representing the item
    var badgeCount: Int, // Badge count for notifications
    )

@Preview
@Composable
fun MainScreenPreview(){
    MainScreen()
}