package com.hockey.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class News(
    val id: String = "", // Unique ID for the news item
    val title: String = "",
    val description: String = "",
    @ServerTimestamp // Automatically sets the timestamp on the server
    val timestamp: Date? = null,
    val detailedInfo: String = "",
    val imageUrl: String = "", // Changed from imageRes (Int) to imageUrl (String)
    val link: String? = null // Link remains the same
)
