package com.hockey.ui.screens.team

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hockey.R
import com.hockey.ui.theme.HockeyTheme

// Define a data class for athletes
data class Athlete(
    val id: Int,
    val name: String,
    val position: String,
    val stats: String,
    val avatarResId: Int // Resource ID for the athlete's avatar
)

// Sample athletes list matching the image
val athletes = listOf(
    Athlete(1, "John Smith", "Forward", "23 Goals", R.drawable.john_smith),
    Athlete(2, "Sarah Johnson", "Midfielder", "15 Assists", R.drawable.sarah_johnson),
    Athlete(3, "Mike Wilson", "Defender", "5 Clean Sheets", R.drawable.mike_wilson)
)


@Composable
fun PlayerManagementScreen(
    onAddAthleteClick: () -> Unit = {}, // Callback for adding an athlete
    onEditAthleteClick: (Athlete) -> Unit = {}, // Callback for editing an athlete
    onContactAthleteClick: (Athlete) -> Unit = {}, // Callback for contacting an athlete
    onDeleteAthleteClick: (Athlete) -> Unit = {} // Callback for deleting an athlete
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        IconButton(
            onClick = {
                (context as? ComponentActivity)?.finish()
            },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
        }

        // Header with title and "Add Athlete" button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Player Management",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium
            )
            Button(
                onClick = onAddAthleteClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                modifier = Modifier.height(36.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add Athlete button"
                )
                Text("Add Athlete", fontSize = 14.sp)
            }
        }

        // Athlete list
        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(athletes) { athlete ->
                AthleteCard(
                    athlete = athlete,
                    onEditClick = { onEditAthleteClick(athlete) },
                    onContactClick = { onContactAthleteClick(athlete) },
                    onDeleteClick = { onDeleteAthleteClick(athlete) }
                )
            }
        }
    }
}

@Composable
fun AthleteCard(
    athlete: Athlete,
    onEditClick: () -> Unit,
    onContactClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Athlete avatar
            Image(
                painter = painterResource(id = athlete.avatarResId),
                contentDescription = "Athlete Avatar",
                modifier = Modifier
                    .size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            // Athlete details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = athlete.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${athlete.position} • ${athlete.stats}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            // Action buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Edit",
                        tint = Color.Gray
                    )
                }
                IconButton(onClick = onContactClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "Contact",
                        tint = Color.Gray
                    )
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Delete",
                        tint = Color.Gray
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerManagementScreenPreview() {
    HockeyTheme {
        PlayerManagementScreen()
    }
}
