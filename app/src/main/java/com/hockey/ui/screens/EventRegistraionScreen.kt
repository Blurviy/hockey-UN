package com.hockey.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@Composable
fun EventRegistrationScreen(
    event: Event, // The selected event
    teamName: String = "", // Optional default team name
    onConfirmRegistration: (String) -> Unit = {}, // Callback for confirmation
    onCancelRegistration: () -> Unit = {} // Callback for cancellation
) {
    var teamNameInput by remember { mutableStateOf(teamName) }
    var contactPerson by remember { mutableStateOf("") }
    var contactEmail by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Icon
        Image(
            painter = painterResource(id = R.drawable.ic_event),
            contentDescription = "Event Icon",
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Page Title
        Text(
            text = "Register for ${event.title}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Event Details
        Text(
            text = "Date: ${event.date}\nLocation: ${event.location}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Team Name Input
        OutlinedTextField(
            value = teamNameInput,
            onValueChange = { teamNameInput = it },
            label = { Text("Team Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Contact Person Input
        OutlinedTextField(
            value = contactPerson,
            onValueChange = { contactPerson = it },
            label = { Text("Contact Person") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Contact Email Input
        OutlinedTextField(
            value = contactEmail,
            onValueChange = { contactEmail = it },
            label = { Text("Contact Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onCancelRegistration,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Cancel")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    if (teamNameInput.isNotBlank() && contactPerson.isNotBlank() && contactEmail.isNotBlank()) {
                        onConfirmRegistration(teamNameInput)
                    }
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Text("Confirm")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EventRegistrationScreenPreview() {
    HockeyTheme {
        EventRegistrationScreen(
            event = Event(
                id = 1,
                title = "Hockey Match: Team A vs Team B",
                date = "March 15, 2025",
                location = "Main Arena"
            )
        )
    }
}
