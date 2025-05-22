package com.hockey.ui.screens.auth

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hockey.R
import com.hockey.ui.screens.Main1Activity
import com.hockey.ui.viewmodels.AuthViewModel
// import kotlin.coroutines.jvm.internal.CompletedContinuation.context

@Composable
fun SignupScreen(
    authViewModel: AuthViewModel = viewModel(),
    onSignupSuccess: () -> Unit = {},
    onBackToLogin: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val uiState by authViewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Handle successful signup
    LaunchedEffect(uiState.isAuthenticated) {
        if (uiState.isAuthenticated) {
            onSignupSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create Account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Name Input
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Email Input
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Password Input
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            enabled = !uiState.isLoading
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Confirm Password Input
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            enabled = !uiState.isLoading,
            isError = confirmPassword.isNotEmpty() && password != confirmPassword
        )

        if (confirmPassword.isNotEmpty() && password != confirmPassword) {
            Text(
                text = "Passwords don't match",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Error message
        uiState.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Sign Up Button
        Button(
            onClick = {
                if (name.isNotBlank() && email.isNotBlank() &&
                    password.isNotBlank() && password == confirmPassword) {
                    authViewModel.signUp(email, password, name)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
            enabled = !uiState.isLoading && name.isNotBlank() &&
                    email.isNotBlank() && password.isNotBlank() &&
                    password == confirmPassword
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.White
                )
            } else {
                Text("Sign Up")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Back to Login Button
        TextButton(
            onClick = onBackToLogin,
            enabled = !uiState.isLoading
        ) {
            Text("Already have an account? Login")
        }

        Spacer(modifier = Modifier.height(8.dp))
        // Continue as Guest
        TextButton(
            onClick = { context.startActivity(Intent(context, Main1Activity::class.java)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_person_add),
                contentDescription = "Home icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Continue to App", color = Color.Blue)
        }
    }
}