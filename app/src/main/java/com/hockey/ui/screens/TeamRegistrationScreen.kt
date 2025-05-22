package com.hockey.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hockey.ui.theme.HockeyTheme
import com.hockey.ui.viewmodel.TeamViewModel
import com.hockey.ui.viewmodel.PlayerViewModel

data class PlayerData(
    val id: Int,
    var name: String = "",
    var email: String = "",
    var mobileNumber: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamRegistrationScreen(
    onNavigateBack: () -> Unit = {},
    teamViewModel: TeamViewModel = hiltViewModel(),
    playerViewModel: PlayerViewModel = hiltViewModel()
) {
    var teamName by remember { mutableStateOf("") }
    var managerName by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var players by remember { mutableStateOf(listOf(PlayerData(1))) }
    var birthCertificateUploaded by remember { mutableStateOf(false) }
    var passportPhotoUploaded by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val isLoading by teamViewModel.isLoading.collectAsState()
    val error by teamViewModel.error.collectAsState()

    // Show success message and navigate back when team is created
    LaunchedEffect(error) {
        if (error == null && isLoading == false && teamName.isNotEmpty()) {
            // Team created successfully, you might want to show a success message
            // and then navigate back or clear the form
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 500.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.Start
            ) {
                // Back button
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }

                Text(
                    text = "Team Registration",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Error display
                error?.let { errorMessage ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Error: $errorMessage",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text(
                    text = "Team Details",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Team Name",
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    value = teamName,
                    onValueChange = { teamName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    singleLine = true,
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Manager Name",
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    value = managerName,
                    onValueChange = { managerName = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    singleLine = true,
                    enabled = !isLoading
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Contact Number",
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    value = contactNumber,
                    onValueChange = { contactNumber = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    singleLine = true,
                    enabled = !isLoading
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
                            players = players + PlayerData(players.size + 1)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        shape = RoundedCornerShape(4.dp),
                        enabled = !isLoading
                    ) {
                        Text("+ ADD PLAYER")
                    }
                }

                players.forEachIndexed { index, player ->
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

                        if (players.size > 1) {
                            IconButton(
                                onClick = {
                                    players = players.filter { it.id != player.id }
                                },
                                enabled = !isLoading
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

                    Text(text = "Player Name")

                    OutlinedTextField(
                        value = player.name,
                        onValueChange = { newName ->
                            val updatedPlayers = players.toMutableList()
                            updatedPlayers[index] = player.copy(name = newName)
                            players = updatedPlayers
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Email Address")

                    OutlinedTextField(
                        value = player.email,
                        onValueChange = { newEmail ->
                            val updatedPlayers = players.toMutableList()
                            updatedPlayers[index] = player.copy(email = newEmail)
                            players = updatedPlayers
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        singleLine = true,
                        enabled = !isLoading
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Mobile Number")

                    OutlinedTextField(
                        value = player.mobileNumber,
                        onValueChange = { newNumber ->
                            val updatedPlayers = players.toMutableList()
                            updatedPlayers[index] = player.copy(mobileNumber = newNumber)
                            players = updatedPlayers
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        singleLine = true,
                        enabled = !isLoading
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
                    contentPadding = PaddingValues(16.dp),
                    enabled = !isLoading
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
                    contentPadding = PaddingValues(16.dp),
                    enabled = !isLoading
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Information",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (teamName.isNotBlank() && managerName.isNotBlank()) {
                            viewModel.addTeam(teamName, managerName, contactNumber)

                            // After successful submission, add players
                            viewModel.teams.value.lastOrNull()?.let { newTeam ->
                                players.forEach { playerData ->
                                    playerViewModel.addPlayer(
                                        teamId = newTeam.teamId,
                                        name = playerData.name,
                                        email = playerData.email,
                                        mobileNumber = playerData.mobileNumber,
                                        birthCertificatePath = if (birthCertificateUploaded) "path_to_file" else null,
                                        passportPhotoPath = if (passportPhotoUploaded) "path_to_file" else null
                                    )
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    enabled = !isLoading && teamName.isNotBlank() && managerName.isNotBlank()
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                        )
                    } else {
                        Text(
                            "SUBMIT REGISTRATION",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(100.dp))
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