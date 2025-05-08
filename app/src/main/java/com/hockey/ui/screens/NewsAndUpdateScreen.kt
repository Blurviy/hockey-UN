package com.hockey.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hockey.R
import com.hockey.ui.theme.HockeyTheme

@Composable
fun NewsAndUpdateScreen(){
    Box (modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "News and Real Time Updates Screen",
            style = MaterialTheme.typography.headlineLarge
        )
    }
}
data class News(
    val title: String,
    val description: String,
    val time: String,
    val detailedInfo: String,
    val imageRes: Int// New field for more details

)


val newsList = listOf(
    News(
        "New Hockey Season Updates!",
        "The new season kicks off next month with exciting games.",
        "March 10, 2025 | 5:30 PM",
        "The upcoming hockey season is expected to be one of the most exciting in recent years, with new teams joining the league and a change in the tournament structure.",
        R.drawable.game
    ),
    News(
        "Team Wins Big Match",
        "The team defeated their rivals in a thrilling game last night.",
        "March 8, 2025 | 10:00 AM",
        "The match was intense, with several highlight moments. The final score was 4-3 in favor of our team, and the crowd went wild during the final seconds.",
        R.drawable.winguys
    ),
    News(
        "Player Injury Update",
        "The star player is expected to make a full recovery in two weeks.",
        "March 5, 2025 | 8:45 AM",
        "After a tough match, the player was injured but is now recovering. Medical staff have assured that the recovery will be swift, and the player will return soon.",
        R.drawable.guyteam
    ),
    News(
        "Upcoming Game Schedule",
        "The next match is against Team X on March 15, 2025.",
        "March 2, 2025 | 2:00 PM",
        "The upcoming game against Team X is highly anticipated. Fans are excited, and tickets are selling out quickly. It promises to be an exciting matchup!",
        R.drawable.winguys
    )
)


@Composable
fun NewsList(modifier: Modifier = Modifier) {
    var selectedNews by remember { mutableStateOf<News?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LazyColumn(modifier = modifier) {
        items(newsList) { news ->
            NewsCard(news) {
                // When a news item is clicked, show the dialog
                selectedNews = news
                showDialog = true
            }
        }
    }

    // Show dialog when a news item is clicked
    if (showDialog && selectedNews != null) {
        NewsDialog(news = selectedNews!!, onDismiss = { showDialog = false })
    }
}

@Composable
fun NewsCard(news: News, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        //elevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(id = news.imageRes),
                contentDescription = news.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = news.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = news.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = news.time, style = MaterialTheme.typography.bodySmall)
        }
    }
}


@Composable
fun NewsDialog(news: News, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = news.title)
        },
        text = {
            Column {
                Text(text = news.description)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Date/Time: ${news.time}")
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
        NewsList()
    }
}


