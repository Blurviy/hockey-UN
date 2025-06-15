package com.hockey.ui.screens.news

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hockey.data.model.News
import com.hockey.ui.theme.HockeyTheme
import com.hockey.ui.viewmodels.NewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsCreationScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    newsId: String? = null, // Accept an optional newsId for editing
    newsViewModel: NewsViewModel = viewModel()
) {
    // Determine if we are in "edit" mode
    val isEditMode = newsId != null

    // State for form fields
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var detailedInfo by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var link by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // If in edit mode, fetch the news article and populate the fields
    LaunchedEffect(key1 = newsId) {
        if (isEditMode) {
            val existingNews = newsViewModel.newsList.value.find { it.id == newsId }
            existingNews?.let { news ->
                title = news.title
                description = news.description
                detailedInfo = news.detailedInfo
                imageUrl = news.imageUrl
                link = news.link ?: ""
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Change title based on whether we are creating or updating
                    Text(if (isEditMode) "Update News Article" else "Create News Article")
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Short Description") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = detailedInfo, onValueChange = { detailedInfo = it }, label = { Text("Detailed Information") }, modifier = Modifier.fillMaxWidth().height(120.dp))
            OutlinedTextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("Image URL") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = link, onValueChange = { link = it }, label = { Text("External Link (Optional)") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    isLoading = true
                    val newsData = News(
                        id = newsId ?: "", // Use existing ID or it will be ignored by Firestore on creation
                        title = title,
                        description = description,
                        detailedInfo = detailedInfo,
                        imageUrl = imageUrl,
                        link = link.ifBlank { null }
                        // Timestamp is handled by the server/ViewModel
                    )

                    if (isEditMode) {
                        // --- UPDATE LOGIC ---
                        newsViewModel.updateNews(newsId!!, newsData) { success, errorMessage ->
                            isLoading = false
                            if (success) {
                                Toast.makeText(context, "News updated successfully!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            } else {
                                Toast.makeText(context, "Update Error: $errorMessage", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        // --- ADD LOGIC ---
                        newsViewModel.addNews(newsData) { success, errorMessage ->
                            isLoading = false
                            if (success) {
                                Toast.makeText(context, "News added successfully!", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            } else {
                                Toast.makeText(context, "Creation Error: $errorMessage", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                },
                enabled = !isLoading && title.isNotBlank() && description.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    // Change button text based on mode
                    Text(if (isEditMode) "Update News" else "Add News")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewsCreationScreenPreview() {
    HockeyTheme {
        NewsCreationScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun NewsUpdateScreenPreview() {
    HockeyTheme {
        // Preview for the "update" mode
        NewsCreationScreen(navController = rememberNavController(), newsId = "some-fake-id")
    }
}
