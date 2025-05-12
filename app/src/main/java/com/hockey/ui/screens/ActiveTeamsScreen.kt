package com.hockey.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// HockeyTeam data class
// It is supposed to be Team, but i changed it because of the errors
data class HockeyTeam(
    val name: String,
    val city: String,
    val wins: Int,
    val losses: Int,
    val isActive: Boolean
)

// Mock team list
val mockTeams = listOf(
    HockeyTeam("Falcon Warriors", "New York", 15, 3, true),
    HockeyTeam("Thunder Wolves", "Chicago", 12, 5, true),
    HockeyTeam("Ice Breakers", "Toronto", 10, 7, true),
    HockeyTeam("Blaze Hunters", "Dallas", 18, 1, true),
    HockeyTeam("Shadow Panthers", "Los Angeles", 8, 10, true)
)

// Main ActiveTeamsScreen composable
@Composable
fun ActiveTeamsScreen(teams: List<HockeyTeam>) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (teams.isEmpty()) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "No Active Teams",
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "There are currently no active teams to display.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(teams) { team ->
                    TeamCard(team)
                }
            }
        }
    }
}

// TeamCard composable
@Composable
fun TeamCard(team: HockeyTeam) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = team.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "City: ${team.city}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Wins: ${team.wins}, Losses: ${team.losses}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = if (team.isActive) "Status: Active" else "Status: Inactive",
                color = if (team.isActive) Color.Green else Color.Red,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}

// Preview for testing
@Preview(showBackground = true)
@Composable
fun ActiveTeamsScreenPreview() {
    ActiveTeamsScreen(teams = mockTeams)
}
