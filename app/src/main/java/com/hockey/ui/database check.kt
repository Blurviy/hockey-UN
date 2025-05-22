package com.hockey.ui // Or your appropriate UI package

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun SendMessageToFirebaseButton(
    modifier: Modifier = Modifier,
    messagePath: String = "message", // You can customize the path
    messageValue: String = "Hello, World! - From Compose Button" // Customize the message
) {
    val coroutineScope = rememberCoroutineScope()

    Button(
        onClick = {
            coroutineScope.launch {
                try {
                    // Write a message to the database
                    val database = Firebase.database
                    // You can use a more specific reference if your database URL is not the default
                    // val database = Firebase.database("YOUR_DATABASE_URL")
                    val myRef = database.getReference(messagePath)

                    myRef.setValue(messageValue).await() // Use .await() for suspending behavior

                    Log.d("FirebaseSend", "Message sent successfully to $messagePath")
                    // You could also show a Snackbar or Toast here
                } catch (e: Exception) {
                    Log.e("FirebaseSend", "Error sending message to Firebase", e)
                    // Handle the error, e.g., show an error message to the user
                }
            }
        },
        modifier = modifier
    ) {
        Text("Send Message to Firebase")
    }
}

// Example usage in a screen:
@Composable
fun MyFirebaseScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Firebase Realtime DB Test")
        SendMessageToFirebaseButton(
            messagePath = "greetings/user123", // Example of a more specific path
            messageValue = "Welcome to the app!"
        )
        SendMessageToFirebaseButton() // Uses default path and message
    }
}

@Preview(showBackground = true)
@Composable
fun MyFirebaseScreenPreview() {
    MyFirebaseScreen()
}