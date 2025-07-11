package com.hockey.ui.screens.settings

import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.navigation.compose.rememberNavController
import com.hockey.ui.theme.HockeyTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    var isDarkMode by remember { mutableStateOf(isDarkMode) }
    var isNotificationsEnabled by remember { mutableStateOf(true) }
    var allowBackgroundData by remember { mutableStateOf(false) }
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
            label = "Switch Theme",
            description = "Switch between light and dark theme",
            buttonLabel = if (isDarkMode) "Switch to Light" else "Switch to Dark",
            onButtonClick = { isDarkMode = !isDarkMode }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Notifications Switch
        SettingsOption(
            label = "Enable Notifications",
            description = "Turn on/off notifications",
            buttonLabel = if (isNotificationsEnabled) "Disable" else "Enable",
            onButtonClick = { isNotificationsEnabled = !isNotificationsEnabled }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Allow Background Data Switch
        SettingsOption(
            label = "Allow Background Data",
            description = "Enable or disable background data usage",
            buttonLabel = if (allowBackgroundData) "Disable" else "Enable",
            onButtonClick = { allowBackgroundData = !allowBackgroundData }
        )

        Spacer(modifier = Modifier.height(16.dp))

//        // User Name Edit
//        SettingsOption(
//            label = "Username",
//            description = "Change your username",
//            content = {
//                OutlinedTextField(
//                    value = userName,
//                    onValueChange = { userName = it },
//                    label = { Text("Enter new username") },
//                    modifier = Modifier.fillMaxWidth()
//                )
//            }
//        )

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
    buttonLabel: String,
    onButtonClick: () -> Unit,
    content: @Composable () -> Unit = {}
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
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

            // Optionally display additional content
            if (content != {}) {
                Spacer(modifier = Modifier.height(12.dp))
                content()
            }

            // Button
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onButtonClick,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = buttonLabel)
            }
        }
    }
}


@Composable
fun Settings() {
    var isSystemDarkTheme = isSystemInDarkTheme() // Detect system theme
    var isDarkMode by remember { mutableStateOf(isSystemDarkTheme) }

    HockeyTheme(darkTheme = isDarkMode) {
        SettingsScreen(
            navController = rememberNavController(),
            isDarkMode = isDarkMode,
            onThemeChange = { isDarkMode = it } // Update theme state
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    Settings()
}

