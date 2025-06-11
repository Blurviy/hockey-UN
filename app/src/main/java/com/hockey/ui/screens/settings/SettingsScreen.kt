package com.hockey.ui.screens.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hockey.ui.theme.HockeyTheme

@Composable
fun SettingsScreen(modifier: Modifier = Modifier, navController: NavController) {
    // States to manage settings options
    var isDarkMode by remember { mutableStateOf(false) }
    var isNotificationsEnabled by remember { mutableStateOf(true) }
    var userName by remember { mutableStateOf("John Doe") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Title of the screen
        Text(
            text = "Settings",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Dark Mode Switch
        SettingsOption(
            label = "Enable Dark Mode",
            description = "Switch between light and dark theme",
            isChecked = isDarkMode,
            onCheckedChange = { isDarkMode = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Notifications Switch
        SettingsOption(
            label = "Enable Notifications",
            description = "Turn on/off notifications",
            isChecked = true,
            onCheckedChange = { isNotificationsEnabled = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User Name Edit
        SettingsOption(
            label = "Username",
            description = "Change your username",
            content = {
                OutlinedTextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Enter new username") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save Button
        Button(
            onClick = { /* Save changes logic here */ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Save Changes", color = MaterialTheme.colorScheme.onPrimary)
        }
    }
}

@Composable
fun SettingsOption(
    label: String,
    description: String,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth(),
        //elevation = CardDefaults.elevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Label Text
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )

            // Description Text
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )

            // Optionally display switch or content
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (content != {}) {
                    content() // Custom content (e.g., text field for user input)
                } else {
                    Switch(
                        checked = isChecked,
                        onCheckedChange = onCheckedChange
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    HockeyTheme {
        SettingsScreen(navController = NavController(LocalContext.current))
    }
}
