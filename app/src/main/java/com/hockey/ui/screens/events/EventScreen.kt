package com.hockey.ui.screens.events

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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

    onEventClick: (Event) -> Unit = {}, // Default empty lambda for preview
    onRegisterTeamClick: (Event) -> Unit = {},
    onAddEventClick: (Event) -> Unit = {} /**TODO add an event creation screen**/
) {
    var selectedEvent by remember { mutableStateOf<Event?>(null) } // Explicitly allow null values
    var showRegistrationScreen by remember { mutableStateOf(false) }

    if (showRegistrationScreen && selectedEvent != null) {
        // Show the registration screen when an event is selected
        EventRegistrationScreen(
            event = selectedEvent!!,
            onConfirmRegistration = { teamName ->
                // Handle registration confirmation
                showRegistrationScreen = false // Return to event list after confirmation
            },
            onCancelRegistration = {
                // Handle cancellation
                showRegistrationScreen = false // Return to event list
            }
        )
    } else {
        // SHow the Event List
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header with title and filter icon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Event Entries",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_filter_alt), // Replace with actual filter icon resource
                    contentDescription = "Filter",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { /* Handle filter click */ }
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(events) { event ->
                    EventCard(
                        event = event,
                        onClick = { onEventClick(event) },
                        onRegisterTeamClick = {
                            selectedEvent = event // sets the current selected event
                            showRegistrationScreen = true // Navigate to Evernt registration
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun function(): (Event) -> Unit = {}

@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    onRegisterTeamClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Event title and status row
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

            // Date row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar_today), // Replace with actual calendar icon resource
                    contentDescription = "Date",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = event.date,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Location row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_location), // Replace with actual location icon resource
                    contentDescription = "Location",
                    modifier = Modifier.size(16.dp),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = event.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Team count row (if active)
            if (event.teamCount != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_groups), // Replace with actual teams icon resource
                        contentDescription = "Teams",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${event.teamCount} Teams Registered",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            // Register Team button
            Button(
                onClick = onRegisterTeamClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add), // Replace with actual register icon resource
                    contentDescription = "Register Icon",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("REGISTER TEAM")
            }
        }
    }
}

@Composable
fun EventScreenDropDown(){
    var expanded by remember { mutableStateOf(false) } // State to control the visibility of the drop down menu

    val menuItems = listOf (
        "Unread" to Icons.Default.Message,
        "Favorites" to Icons.Default.Favorite,
        "Contacts" to Icons.Default.Person,
        "Non-contacts" to Icons.Default.Person,
        "Groups" to Icons.Default.Group,
        "Drafts" to Icons.Default.Message
    )

    /**TODO Implementation of code for the drop down**/
}

@Preview(showBackground = true)
@Composable
fun EventScreenDropDownPreview() {
    HockeyTheme {
        EventScreenDropDown()
    }
}

@Preview(showBackground = true)
@Composable
fun EventScreenPreview() {
    HockeyTheme {
        EventScreen()
    }
}