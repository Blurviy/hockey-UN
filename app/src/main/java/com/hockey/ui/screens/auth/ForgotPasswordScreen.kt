package com.hockey.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hockey.utils.AppUtil
import android.util.Patterns

@Composable
fun ForgotPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var stage by remember { mutableStateOf(1) } // 1: Enter email, 2: Enter OTP, 3: Reset password
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (stage) {
            1 -> {
                // Enter Email
                Text("Enter your email to reset password", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()
                )
                if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Text(
                        text = "Enter a valid email address",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            // TODO: Implement Firebase OTP sending logic
                            AppUtil.showToast(context, "OTP sent to $email")
                            stage = 2
                        } else {
                            AppUtil.showToast(context, "Please enter a valid email")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                ) {
                    Text("Send OTP")
                }
            }

            2 -> {
                // Enter OTP
                Text("Enter the OTP sent to your email", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = otp,
                    onValueChange = { otp = it },
                    label = { Text("OTP") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (otp.isNotEmpty()) {
                            // TODO: Verify OTP with Firebase
                            AppUtil.showToast(context, "OTP Verified")
                            stage = 3
                        } else {
                            AppUtil.showToast(context, "Please enter the OTP")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = otp.isNotEmpty()
                ) {
                    Text("Verify OTP")
                }
            }

            3 -> {
                // Reset Password
                Text("Enter a new password", fontSize = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation()
                )
                if (newPassword.isNotEmpty() && confirmPassword.isNotEmpty() && newPassword != confirmPassword) {
                    Text(
                        text = "Passwords don't match",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (newPassword.isNotEmpty() && confirmPassword.isNotEmpty() && newPassword == confirmPassword) {
                            // TODO: Reset password with Firebase
                            AppUtil.showToast(context, "Password reset successful")
                            navController.navigate("login")
                        } else {
                            AppUtil.showToast(context, "Please fill in all fields correctly")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = newPassword.isNotEmpty() && confirmPassword.isNotEmpty() && newPassword == confirmPassword
                ) {
                    Text("Reset Password")
                }
            }
        }
    }
}
