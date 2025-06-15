package com.hockey.ui.screens.news

import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.hockey.R
import com.hockey.data.model.News
import com.hockey.ui.theme.HockeyTheme
import com.hockey.ui.viewmodels.NewsViewModel
import com.hockey.utils.AppDropDown
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NewsAndUpdateScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    role: String = "user",
    newsViewModel: NewsViewModel = viewModel()
) {
    val newsList by newsViewModel.newsList.collectAsState()
    var selectedNews by remember { mutableStateOf<News?>(null) }
    var showDetailDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var newsToDelete by remember { mutableStateOf<News?>(null) }
    var showWebView by remember { mutableStateOf(false) }
    var currentUrl by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        if (showWebView) {
            // --- WebView Section ---
            IconButton(
                onClick = { showWebView = false },
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.Start)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
            WebViewScreen(url = currentUrl)
        } else {
            // --- Main News List Section ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { (context as? ComponentActivity)?.finish() },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
                AppDropDown(
                    menuItems = listOf(
                        Triple("Recent", Icons.Default.Update) { /* Handle filter */ },
                        Triple("Popular", Icons.Default.ThumbUp) { /* Handle filter */ },
                        Triple("Archived", Icons.Default.Archive) { /* Handle filter */ }
                    )
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
            ) {
                items(newsList) { news ->
                    NewsCard(
                        news = news,
                        role = role, // Pass the user role to the card
                        onClick = {
                            if (!news.link.isNullOrBlank()) {
                                // Open link in WebView within the app
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(news.link))
                                context.startActivity(intent)
                            } else {
                                selectedNews = news
                                showDetailDialog = true
                            }
                        },
                        onUpdateClick = {
                            // Navigate to the creation screen in "edit" mode
                            navController.navigate("news_creation/${news.id}")
                        },
                        onDeleteClick = {
                            newsToDelete = news
                            showDeleteDialog = true
                        }
                    )
                }
            }

            if (showDetailDialog && selectedNews != null) {
                NewsDialog(news = selectedNews!!, onDismiss = { showDetailDialog = false })
            }

            // Confirmation dialog for deleting news
            if (showDeleteDialog && newsToDelete != null) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    title = { Text("Confirm Deletion") },
                    text = { Text("Are you sure you want to delete this news article: '${newsToDelete!!.title}'?") },
                    confirmButton = {
                        Button(
                            onClick = {
                                newsViewModel.deleteNews(newsToDelete!!.id) { success, error ->
                                    if (success) {
                                        Toast.makeText(context, "News deleted", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
                                    }
                                }
                                showDeleteDialog = false
                                newsToDelete = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) { Text("Delete") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDeleteDialog = false }) {
                            Text("Cancel")
                        }
                    }
                )
            }

            if (role == "admin") {
                FloatingActionButton(
                    onClick = { navController.navigate("news_creation") },
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add News")
                }
            }
        }
    }
}


@Composable
fun NewsCard(
    news: News,
    role: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onUpdateClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    val dateFormatter = remember { SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = news.imageUrl,
                        placeholder = rememberAsyncImagePainter(model = R.drawable.default_img)
                    ),
                    contentDescription = news.title,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = news.title, style = MaterialTheme.typography.titleMedium, maxLines = 2)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = news.description, style = MaterialTheme.typography.bodyMedium, maxLines = 1)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = news.timestamp?.let { dateFormatter.format(it) } ?: "No date",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // *** ADMIN CONTROLS ***
            // Show update and delete buttons only if the role is 'admin'
            if (role == "admin") {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onUpdateClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Update News")
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete News", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }
        }
    }
}

@Composable
fun WebViewScreen(url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient()
                loadUrl(url)
                settings.javaScriptEnabled = true
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun NewsDialog(news: News, onDismiss: () -> Unit) {
    val dateFormatter = remember { SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = news.title) },
        text = {
            Column {
                Text(text = news.description)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Date: ${news.timestamp?.let { dateFormatter.format(it) } ?: "N/A"}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Details: ${news.detailedInfo}")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun NewsPreview() {
    HockeyTheme {
        NewsAndUpdateScreen(navController = rememberNavController(), role = "admin", newsViewModel = viewModel())
    }
}
