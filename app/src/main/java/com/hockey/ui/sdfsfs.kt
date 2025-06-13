package com.hockey.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@Composable
fun FirebaseButton() {
    Button(onClick = {
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("yy")
            .addOnSuccessListener {
                // Write was successful!
            }
            .addOnFailureListener {
                // Write failed
            }
    }) {
        Text("Send Message to Firebase")
    }
}

@Preview
@Composable
fun FirebaseButtonPreview() {
    FirebaseButton()
}