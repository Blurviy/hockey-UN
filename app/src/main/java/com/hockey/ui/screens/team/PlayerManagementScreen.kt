package com.hockey.ui.screens.team

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.core.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hockey.R
import com.hockey.ui.theme.HockeyTheme
import androidx.core.net.toUri

// Athlete data class
data class Athlete(
    val id: Int,
    val name: String,
    val position: String,
    val stats: String,
    val avatarResId: Int,
    val email: String
)

// ViewModel to manage athletes list
class PlayerViewModel : ViewModel() {
    private val _athletes = mutableStateListOf(
        Athlete(1, "John Smith", "Forward", "23 Goals", R.drawable.john_smith, "john.smith@example.com"),
        Athlete(2, "Sarah Johnson", "Midfielder", "15 Assists", R.drawable.sarah_johnson, "sarah.johnson@example.com"),
        Athlete(3, "Mike Wilson", "Defender", "5 Clean Sheets", R.drawable.mike_wilson, "mike.wilson@example.com")
    )
    val athletes: List<Athlete> get() = _athletes

    fun updateAthlete(updatedAthlete: Athlete) {
        val index = _athletes.indexOfFirst { it.id == updatedAthlete.id }
        if (index != -1) {
            _athletes[index] = updatedAthlete
        }
    }

    fun deleteAthlete(athlete: Athlete) {
        _athletes.removeIf { it.id == athlete.id }
    }
}

@Composable
fun PlayerManagementScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PlayerViewModel = viewModel(),
    onAddAthleteClick: () -> Unit = {}
) {
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

        LazyColumn(
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(viewModel.athletes) { athlete ->
                AthleteCard(
                    athlete = athlete,
                    onEditAthlete = { updatedAthlete -> viewModel.updateAthlete(updatedAthlete) },
                    onContactAthlete = {
                        val context = LocalContext.current
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = "mailto:${athlete.email}".toUri()
                            putExtra(Intent.EXTRA_SUBJECT, "Contacting ${athlete.name}")
                        }
                        context.startActivity(Intent.createChooser(intent, "Send Email"))
                    },
                    onDeleteAthlete = { viewModel.deleteAthlete(athlete) }
                )
            }
        }
    }
}

@Composable
fun AthleteCard(
    athlete: Athlete,
    onEditAthlete: (Athlete) -> Unit,
    onContactAthlete: () -> Unit,
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
            if (isEditing) {
                Column(
                    modifier = Modifier.weight(1f),
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
                }
            } else {
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
            }

            // Action buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = { isEditing = !isEditing }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = "Edit",
                        tint = Color.Gray
                    )
                }
                IconButton(onClick = onContactAthlete) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_email),
                        contentDescription = "Contact",
                        tint = Color.Gray
                    )
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete),
                        contentDescription = "Delete",
                        tint = Color.Gray
                    )
                }
            }
        }
    }

    // Delete confirmation dialog
    if (showDeleteDialog) {
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
        PlayerManagementScreen(navController = rememberNavController())
    }
}