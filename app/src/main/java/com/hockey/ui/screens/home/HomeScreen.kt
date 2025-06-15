package com.hockey.ui.screens.home

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hockey.R
import com.hockey.data.model.News
import com.hockey.navigation.AppScreen
import com.hockey.ui.screens.news.NewsCard
import com.hockey.ui.screens.news.NewsDialog
import com.hockey.ui.theme.HockeyTheme
import com.hockey.ui.viewmodels.NewsViewModel
import com.hockey.utils.AppDropDown

// Data class for QuickStats cards
data class QuickStat(
    val title: String,
    val value: String,
    val icon: ImageVector,
    val onClickRoute: String?
)

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    role: String,
    newsViewModel: NewsViewModel = viewModel() // Inject the NewsViewModel
) {
    // Collect the news list from the ViewModel
    val newsList by newsViewModel.newsList.collectAsState()

    // State for managing dialogs
    var selectedNews by remember { mutableStateOf<News?>(null) }
    var showDetailDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var newsToDelete by remember { mutableStateOf<News?>(null) }
    val context = LocalContext.current


    // Define QuickStats cards
    val quickStats = listOf(
        QuickStat(
            title = "Active Teams",
            value = "12",
            icon = Icons.Outlined.People,
            onClickRoute = "active_teams"
        ),
        QuickStat(
            title = "Upcoming Events",
            value = "5",
            icon = Icons.Outlined.Schedule,
            onClickRoute = null // Navigation is handled in the onClick lambda
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp, top = 10.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // --- Welcome Section ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 18.dp, end = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Welcome Back!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            AppDropDown(
                menuItems = listOf(
                    Triple("Login", painterResource(id = R.drawable.ic_login)) {
                        navController.navigate(AppScreen.Login.route)
                    },
                    Triple("Signup", painterResource(id = R.drawable.ic_person_add)) {
                        navController.navigate(AppScreen.Signup.route)
                    }
                )
            )
        }
        Text(
            text = "Manage your sports events and teams efficiently.",
            fontSize = 16.sp,
            color = Color.Gray
        )

        // --- Quick Stats Section ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            quickStats.forEach { stat ->
                QuickStatsCard(
                    title = stat.title,
                    value = stat.value,
                    icon = stat.icon,
                    onClick = {
                        if (stat.title == "Upcoming Events") {
                            when (role) {
                                "manager" -> navController.navigate("event_list_manager")
                                "player" -> navController.navigate("event_list_player")
                                "admin" -> navController.navigate("event_list_admin")
                                else -> navController.navigate("event_list_noLogin")
                            }
                        } else {
                            stat.onClickRoute?.let { navController.navigate(it) }
                        }
                    }
                )
            }
        }

        // --- Recent News Section ---
        Text(
            text = "Recent News & Activities",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(top = 8.dp)
        ) {
            items(newsList) { news ->
                NewsCard(
                    news = news,
                    role = role, // Pass role to show/hide admin controls
                    onClick = {
                        selectedNews = news
                        showDetailDialog = true
                    },
                    onUpdateClick = {
                        // Navigate to the creation screen with the news ID for editing
                        navController.navigate("news_creation/${news.id}")
                    },
                    onDeleteClick = {
                        // Show confirmation dialog before deleting
                        newsToDelete = news
                        showDeleteDialog = true
                    }
                )
            }
        }
    }

    // --- Dialogs ---
    if (showDetailDialog && selectedNews != null) {
        NewsDialog(news = selectedNews!!, onDismiss = { showDetailDialog = false })
    }

    if (showDeleteDialog && newsToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Confirm Deletion") },
            text = { Text("Are you sure you want to delete this news article: '${newsToDelete!!.title}'?") },
            confirmButton = {
                Button(
                    onClick = {
                        newsViewModel.deleteNews(newsToDelete!!.id) { success, error ->
                            val message = if (success) "News deleted" else "Error: $error"
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                        showDeleteDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }
}

@Composable
fun QuickStatsCard(
    title: String,
    value: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2D3748),
            contentColor = Color.White
        ),
        modifier = Modifier
            .width(180.dp)
            .height(100.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(icon, contentDescription = title, modifier = Modifier.size(24.dp))
            Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(title, fontSize = 14.sp)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HockeyTheme {
        HomeScreen(navController = NavController(LocalContext.current), role = "admin")
    }
}
