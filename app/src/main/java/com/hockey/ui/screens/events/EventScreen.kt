package com.hockey.ui.screens.events

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Drafts
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hockey.R
import com.hockey.ui.theme.HockeyTheme
import com.hockey.utils.AppDropDown

// Define a data class for events
data class Event(
    val id: Int,
    val title: String,
    val date: String,
    val location: String,
    val teamCount: Int? = null,
    val isActive: Boolean = false
)

// Sample events list
val events = listOf(
    Event(1, "Hockey Match: Team A vs Team B", "March 15, 2025", "Main Arena"),
    Event(2, "Training Camp", "March 20, 2025", "Training Center"),
    Event(3, "Fan Meet & Greet", "March 25, 2025", "Community Hall"),
    Event(4, "Championship Final", "April 1, 2025", "National Stadium")
)

@Composable
fun EventScreen(
    userRole: String, // Pass the role dynamically
    onEventClick: (Event) -> Unit = {}, // Callback for navigating to Event Details
    onRegisterTeamClick: (Event) -> Unit = {}, // Callback for Team Registration
    onAddEventClick: () -> Unit = {} // Callback for Admin Event Creation
) {
    var selectedEvent by remember { mutableStateOf<Event?>(null) }
    var showRegistrationScreen by remember { mutableStateOf(false) }
    var filter by remember { mutableStateOf("All") } // State for filtering events

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
            }
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
                    text = "Event Entries ($filter)",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )

                // Dropdown for Filtering
                AppDropDown(
                    menuItems = listOf(
                        Triple("Unread", Icons.Default.Message) {
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
                items(events) { event ->
                    EventCard(
                        event = event,
                        userRole = userRole,
                        onClick = { onEventClick(event) }, // Pass the click to navigate
                        onRegisterTeamClick = {
                            selectedEvent = event
                            showRegistrationScreen = true
                        }
                    )
                }
            }

            // Show "Add Event" button for Admin
            if (userRole == "Admin") {
                Button(
                    onClick = onAddEventClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Add Event")
                }
            }
        }
    }
}


@Composable
fun EventScreenDropDown(
    onFilterSelected: (String) -> Unit = {} // Callback for selected filter
) {
    var expanded by remember { mutableStateOf(false) } // State to control the visibility of the drop-down menu

    val menuItems = listOf(
        "Unread" to Icons.Default.Message,
        "Favorites" to Icons.Default.Favorite,
        "Groups" to Icons.Default.Group,
        "Drafts" to Icons.Default.Message
    )

    Box {
        // Filter Icon
        Icon(
            imageVector = Icons.Filled.MoreVert, // Replace with actual filter icon resource
            contentDescription = "More options",
            modifier = Modifier
                .size(24.dp)
                .clickable { expanded = true } // Show the dropdown when clicked
        )

        // Dropdown Menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false } // Close dropdown when clicked outside
        ) {
            menuItems.forEach { (label, icon) ->
                DropdownMenuItem(
                    onClick = {
                        onFilterSelected(label) // Callback with the selected filter
                        expanded = false // Close the dropdown
                    },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = label)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EventCard(
    event: Event,
    userRole: String, // Role determines visibility of buttons
    onClick: () -> Unit,
    onRegisterTeamClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }, // Trigger navigation when clicked
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                if (event.isActive) {
                    Text(
                        text = "ACTIVE",
                        color = Color.Green,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .background(
                                Color.Green.copy(alpha = 0.1f),
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            // Event Date and Location
            Row {
                Text(text = "Date: ${event.date}")
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = "Location: ${event.location}")
            }

            // Show Register Button for Manager Only
            if (userRole == "Manager") {
                Button(
                    onClick = onRegisterTeamClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("REGISTER TEAM")
                }
            }
        }
    }
}

@Composable
fun EventDetailsScreen(
    event: Event,
    userRole: String,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = event.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(text = "Date: ${event.date}")
        Text(text = "Location: ${event.location}")
        if (event.teamCount != null) {
            Text(text = "Teams Registered: ${event.teamCount}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back Button
        Button(onClick = onBackClick) {
            Text("Back")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun EventScreenPreview() {
    HockeyTheme {
        EventScreen(userRole = "manager")
    }
}

@Preview
@Composable
fun EventDetailsScreenPreview() {
    HockeyTheme {
        EventDetailsScreen(
            event = Event(
                id = 1,
                title = "Hockey Match: Team A vs Team B",
                date = "March 15, 2025",
                location = "Main Arena"),
            userRole = "manager",
            onBackClick = {}

        )
    }
}
