// Create a new file: ui/screens/SampleRoomUsageScreen.kt
package com.hockey.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hockey.data.entities.Team
import com.hockey.ui.viewmodel.TeamViewModel

@Composable
fun SampleRoomUsageScreen(
    teamViewModel: TeamViewModel = hiltViewModel()
) {
    val teams by teamViewModel.teams.collectAsState()
    val isLoading by teamViewModel.isLoading.collectAsState()
    val error by teamViewModel.error.collectAsState()

    var teamName by remember { mutableStateOf("") }
    var managerName by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Room Database Demo",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Add Team Form
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Add New Team",
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = teamName,
                    onValueChange = { teamName = it },
                    label = { Text("Team Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = managerName,
                    onValueChange = { managerName = it },
                    label = { Text("Manager Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = contactNumber,
                    onValueChange = { contactNumber = it },
                    label = { Text("Contact Number") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        if (teamName.isNotBlank() && managerName.isNotBlank()) {
                            teamViewModel.addTeam(teamName, managerName, contactNumber)
                            teamName = ""
                            managerName = ""
                            contactNumber = ""
                        }
                    },
                    modifier = Modifier.align(Alignment.End),
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Add Team")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Error Display
        error?.let { errorMessage ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = "Error: $errorMessage",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Teams List
        Text(
            text = "Teams (${teams.size})",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(teams) { team ->
                TeamItem(
                    team = team,
                    onDeleteClick = { teamViewModel.deleteTeam(team) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TeamItem(
    team: Team,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = team.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Manager: ${team.managerName}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Contact: ${team.contactNumber}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            TextButton(
                onClick = onDeleteClick
            ) {
                Text("Delete")
            }
        }
    }
}