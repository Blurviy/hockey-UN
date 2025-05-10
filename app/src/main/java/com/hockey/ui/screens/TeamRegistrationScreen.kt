package com.hockey.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hockey.ui.theme.HockeyTheme
import com.hockey.ui.screens.PlayerManagementActivity
import com.hockey.ui.screens.PlayerRegistrationActivity
import com.hockey.ui.screens.TeamRegistrationActivity

data class Team(
    val id: Int,
    var email: String = "",
    var mobileNumber: String = ""
)

@Composable
fun Activity(value: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp), // Slightly more rounded corners
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp), // Increased elevation for better shadow effect
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp) // Padding around the card itself
            .clickable { onClick() } // Adding the clickable modifier to trigger the onClick action
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp) // Space between elements
        ) {
            // Title or main content of the card with improved typography
            Text(
                text = value,
                fontSize = 16.sp, // Increased font size for better readability
                fontWeight = FontWeight.Bold, // Make the text bold for emphasis
                color = Color.Black // Change text color to black for better contrast
            )

            // Optionally, you can add more elements here, for example, a description
            Text(
                text = "Click for more details",
                fontSize = 12.sp,
                color = Color.Gray // Lighter gray for secondary text
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamRegistrationScreen() {
    var teamName by remember { mutableStateOf("") }
    var managerName by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var teams by remember { mutableStateOf<List<Team>>(listOf(Team(1))) }
    var birthCertificateUploaded by remember { mutableStateOf(false) }
    var passportPhotoUploaded by remember { mutableStateOf(false) }

    // State to control visibility of form and buttons
    var formVisible by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

    ) {
        // Back button
        IconButton(
            onClick = {
                (context as? ComponentActivity)?.finish()
            },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        // Title of the screen
        Text(
            text = "Team Management",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Only show the buttons if the form is not visible
        if (!formVisible) {
            // QuickStatsCard for Player Management
            Activity(

                value = "Player Management ",
                onClick = {
                    context.startActivity(Intent(context, PlayerManagementActivity::class.java))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // QuickStatsCard for Player Registration
            Activity(

                value = "Player Registration",
                onClick = {
                    context.startActivity(Intent(context, PlayerRegistrationActivity::class.java))
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // QuickStatsCard for Team Registration
            Activity(

                value = "Team Registration",
                onClick = {
                    formVisible = true // Show the form when button is clicked
                }
            )
        }
    // Show the Team Registration form if formVisible is true
        if (formVisible) {
            // Form to register team details
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Team Registration",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Team Name",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    OutlinedTextField(
                        value = teamName,
                        onValueChange = { teamName = it },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Manager Name",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    OutlinedTextField(
                        value = managerName,
                        onValueChange = { managerName = it },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Contact Number",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    OutlinedTextField(
                        value = contactNumber,
                        onValueChange = { contactNumber = it },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Players",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )

                        Button(
                            onClick = {
                                teams = teams + Team(teams.size + 1)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text("+ ADD PLAYER")
                        }
                    }

                    teams.forEachIndexed { index, player ->
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Player #${player.id}",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )

                            if (teams.size > 1) {
                                IconButton(
                                    onClick = {
                                        teams = teams.filter { it.id != player.id }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Remove player",
                                        tint = Color.Gray
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Email Address")

                        OutlinedTextField(
                            value = player.email,
                            onValueChange = { newEmail ->
                                val updatedPlayers = teams.toMutableList()
                                updatedPlayers[index] = player.copy(email = newEmail)
                                teams = updatedPlayers
                            },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(text = "Mobile Number")

                        OutlinedTextField(
                            value = player.mobileNumber,
                            onValueChange = { newNumber ->
                                val updatedPlayers = teams.toMutableList()
                                updatedPlayers[index] = player.copy(mobileNumber = newNumber)
                                teams = updatedPlayers
                            },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { birthCertificateUploaded = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Gray),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Upload Birth Certificate (PDF)")
                            Text("📎")
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = { passportPhotoUploaded = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        border = BorderStroke(1.dp, Color.Gray),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Upload Passport Photo")
                            Text("📷")
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = { /* Submit Registration Logic */ },
                        modifier = Modifier.fillMaxWidth().height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            "SUBMIT REGISTRATION",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}
@Preview(showBackground = true, heightDp = 1500)
@Composable
fun TeamRegistrationScreenPreview() {
    HockeyTheme {
        TeamRegistrationScreen()
    }
}
