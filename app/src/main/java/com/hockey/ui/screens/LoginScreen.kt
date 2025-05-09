package com.hockey.ui.screens

import android.provider.CalendarContract.Colors
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hockey.R
import com.hockey.ui.theme.HockeyTheme

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit = { _, _ -> }, // Default empty lambda for preview
    onRegisterClick: () -> Unit = {}, // Callback for registration
    onForgotPasswordClick: () -> Unit = {} // Callback for password navigation navigation
    ) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon
        Image(
            painter = painterResource(id = R.drawable.ic_trophy),
            contentDescription = "Trophy Icon",
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // App title
        Text(
            text = "Sports Event Manager",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Tagline
        Text(
            text = "Manage your sports events efficiently",
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Email Input Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter your email") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_email),
                    contentDescription = "Email Icons",
                    modifier = Modifier.size(24.dp)
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Password Input Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Enter your password") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lock),
                    contentDescription = "Password Icon"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Login Button
        Button(
            onClick = { onLoginClick(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_login),
                contentDescription = "Login Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Login")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Register Button
        Button(
            onClick = onRegisterClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(contentColor = Color.DarkGray)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_person_add),
                contentDescription = "Register Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Register")
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Forgot Password
        TextButton(
            onClick = { /* Handle forgot password */ },
            modifier = Modifier.fillMaxWidth()
            ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_lock_reset),
                contentDescription = "Forgot Password Icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Forgot Password?", color = Color.Blue)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    HockeyTheme {
        LoginScreen()
    }
}