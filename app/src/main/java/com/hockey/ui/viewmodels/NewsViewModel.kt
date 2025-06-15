package com.hockey.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.hockey.data.model.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class NewsViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val newsCollection = db.collection("news")

    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> = _newsList

    init {
        fetchNews()
    }

    private fun fetchNews() {
        newsCollection.orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshots, error ->
                if (error != null) { return@addSnapshotListener }

                snapshots?.let {
                    // Map documents to News objects, including the document ID
                    val news = it.documents.mapNotNull { doc ->
                        doc.toObject(News::class.java)?.copy(id = doc.id)
                    }
                    _newsList.value = news
                }
            }
    }

    fun addNews(
        news: News,
        onResult: (success: Boolean, errorMessage: String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                newsCollection.add(news).await()
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.localizedMessage)
            }
        }
    }

    // New function to update an existing news article
    fun updateNews(
        newsId: String,
        updatedNews: News,
        onResult: (success: Boolean, errorMessage: String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                newsCollection.document(newsId).set(updatedNews).await()
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.localizedMessage)
            }
        }
    }

    // New function to delete a news article
    fun deleteNews(
        newsId: String,
        onResult: (success: Boolean, errorMessage: String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                newsCollection.document(newsId).delete().await()
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.localizedMessage)
            }
        }
    }
}
