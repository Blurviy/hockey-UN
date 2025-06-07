package com.hockey.ui.screens.events

import com.hockey.utils.FilteredEvents
import com.hockey.utils.SampleEvents
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.hockey.ui.screens.events.Event
import com.hockey.ui.screens.events.EventCard
import com.hockey.ui.screens.events.EventRegistrationScreen
import com.hockey.ui.theme.HockeyTheme
import com.hockey.utils.AppDropDown

// Sample events list (you can replace it with your dynamic data source)


@Composable
fun ManagerEventScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onEventClick: (Event) -> Unit = {}, // Callback for navigating to Event Details
    onRegisterTeamClick: (Event) -> Unit = {}, // Callback for Team Registration
) {
    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    var showRegistrationScreen by remember { mutableStateOf(false) }
    var filter by remember { mutableStateOf("All") } // State for filtering events

    val events = SampleEvents()
    val filteredEvents = FilteredEvents(events, filter)

    // Display the registration screen if selected
    if (showRegistrationScreen && selectedEvent != null) {
        EventRegistrationScreen(
            event = selectedEvent!!,
            onConfirmRegistration = { teamName ->
                showRegistrationScreen = false
                // Handle registration (for now, print/log success)
            },
            onCancelRegistration = {
                showRegistrationScreen = false
            },
            navController = NavController(LocalContext.current)
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 18.dp, end = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Manager Event Entries ($filter)",
                    fontSize = 24.sp,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )

                // Dropdown for Filtering
                AppDropDown(
                    menuItems = listOf(
                        Triple("Unread", Icons.AutoMirrored.Filled.Message) {
                            // navController.navigate("unread_screen")
                        },
                        Triple("Favorites", Icons.Default.Favorite) {
                            // navController.navigate("favorites_screen")
                        },
                        Triple("Groups", Icons.Default.Group) {
                            // navController.navigate("groups_screen")
                        }
                    )
                )
            }

            // List of Events
            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(filteredEvents) { event ->
                    EventCard(
                        event = event,
                        userRole = "Manager", // Static indication for manager
                        onClick = { onEventClick(event) }, // Pass the click to navigate
                        onRegisterTeamClick = {
                            selectedEvent = event
                            showRegistrationScreen = true
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManagerEventScreenPreview() {
    HockeyTheme {
        ManagerEventScreen(navController = NavController(LocalContext.current))
    }
}
