package com.hockey.ui.screens.team

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hockey.R
import com.hockey.ui.theme.HockeyTheme

// Athlete data class
data class Athlete(
    val id: Int,
    val name: String,
    val position: String,
    val stats: String,
    val avatarResId: Int,
    val email: String
)

@Composable
fun PlayerManagementScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    role: String = "player" // Default role is "player", can be "manager" or "admin"
) {
    val context = LocalContext.current
    var athletes by remember { mutableStateOf(
        listOf(
            Athlete(1, "John Smith", "Forward", "23 Goals", R.drawable.john_smith, "john.smith@example.com"),
            Athlete(2, "Sarah Johnson", "Midfielder", "15 Assists", R.drawable.sarah_johnson, "sarah.johnson@example.com"),
            Athlete(3, "Mike Wilson", "Defender", "5 Clean Sheets", R.drawable.mike_wilson, "mike.wilson@example.com")
        )
    ) }
    var showAddDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

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
            if (role == "manager" || role == "admin") {
                Button(
                    onClick = { showAddDialog = true },
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
        }

        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(athletes) { athlete ->
                AthleteCard(
                    athlete = athlete,
                    context = context,
                    role = role,
                    onEditAthlete = { updatedAthlete ->
                        athletes = athletes.map { if (it.id == updatedAthlete.id) updatedAthlete else it }
                    },
                    onDeleteAthlete = {
                        athletes = athletes.filter { it.id != athlete.id }
                    }
                )
            }
        }
    }

    if (showAddDialog && (role == "manager" || role == "admin")) {
        AddAthleteDialog(
            onDismiss = { showAddDialog = false },
            onAddAthlete = { name, position, stats, email ->
                val newId = athletes.maxOfOrNull { it.id }?.plus(1) ?: 1
                val newAthlete = Athlete(newId, name, position, stats, R.drawable.john_smith, email)
                athletes = athletes + newAthlete
                showAddDialog = false
            }
        )
    }
}

@Composable
fun AddAthleteDialog(
    onDismiss: () -> Unit,
    onAddAthlete: (String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var stats by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add New Athlete") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = position,
                    onValueChange = { position = it },
                    label = { Text("Position") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = stats,
                    onValueChange = { stats = it },
                    label = { Text("Stats") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (name.isNotBlank() && position.isNotBlank() && stats.isNotBlank() && email.isNotBlank()) {
                        onAddAthlete(name, position, stats, email)
                    }
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun AthleteCard(
    athlete: Athlete,
    context: android.content.Context,
    role: String,
    onEditAthlete: (Athlete) -> Unit,
    onDeleteAthlete: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isEditing by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf(athlete.name) }
    var position by remember { mutableStateOf(athlete.position) }
    var stats by remember { mutableStateOf(athlete.stats) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = modifier.fillMaxWidth(),
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
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            // Athlete details or edit fields
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(if (isEditing) 8.dp else 4.dp)
            ) {
                if (isEditing) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = position,
                        onValueChange = { position = it },
                        label = { Text("Position") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = stats,
                        onValueChange = { stats = it },
                        label = { Text("Stats") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextButton(onClick = { isEditing = false }) {
                            Text("Cancel")
                        }
                        TextButton(
                            onClick = {
                                onEditAthlete(
                                    athlete.copy(
                                        name = name,
                                        position = position,
                                        stats = stats
                                    )
                                )
                                isEditing = false
                            }
                        ) {
                            Text("Save")
                        }
                    }
                } else {
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
            }

            // Action buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (role == "manager" || role == "admin") {
                    IconButton(onClick = { isEditing = !isEditing }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.Gray
                        )
                    }
                }
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:${athlete.email}")
                        putExtra(Intent.EXTRA_SUBJECT, "Contacting ${athlete.name}")
                    }
                    context.startActivity(Intent.createChooser(intent, "Send Email"))
                }) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Contact",
                        tint = Color.Gray
                    )
                }
                if (role == "manager" || role == "admin") {
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }

    // Delete confirmation dialog visible only to manager or admin
    if (showDeleteDialog && (role == "manager" || role == "admin")) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirm Delete") },
            text = { Text("Are you sure you want to delete ${athlete.name}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteAthlete()
                        showDeleteDialog = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerManagementScreenPreview() {
    HockeyTheme {
        PlayerManagementScreen(navController = rememberNavController(), role = "player") // Preview with player role
    }
}