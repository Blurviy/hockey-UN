package com.hockey.ui.screens.auth

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.hockey.utils.AppUtil
import com.hockey.R
import com.hockey.navigation.AppScreen
import com.hockey.ui.theme.HockeyTheme
import com.hockey.ui.viewmodels.AuthViewModel
import com.hockey.utils.RoleButton

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
) {
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon
        Image(
            painter = painterResource(id = R.drawable.nhu_logo),
            contentDescription = "Trophy Icon",
            modifier = Modifier.size(240.dp)
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
                    contentDescription = "Email Icon",
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
            onClick = {
                authViewModel.login(email, password) { success, errorMessage ->
                    if (success) {
                        // navController.navigate("home")
                        navController.navigate("fan_main") {
                            popUpTo("auth") { inclusive = true }
                        }
                    } else {
                        // println("Login failed: $errorMessage")
                        AppUtil.showToast(context, message = errorMessage ?: "Something went wrong")
                    }
                }
            },
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
            onClick = { navController.navigate(AppScreen.Signup.route) },
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

        Spacer(modifier = Modifier.height(16.dp))

        // Role-specific Login Options
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            item {
                RoleButton(
                    label = "Continue as Guest",
                    iconRes = R.drawable.ic_person_add,
                    color = Color.Blue
                ) {
                    navController.navigate(AppScreen.NoLoginMain.route)
                }
            }
//            item {
//                RoleButton(
//                    label = "Manager Login",
//                    iconRes = R.drawable.ic_person,
//                    color = Color.Green
//                ) {
//                    navController.navigate("managerMain")
//                }
//            }
//            item {
//                RoleButton(
//                    label = "Fan Login",
//                    iconRes = R.drawable.ic_star,
//                    color = Color.Magenta
//                ) {
//                    navController.navigate("fanMain")
//                }
//            }
//            item {
//                RoleButton(
//                    label = "Player Login",
//                    iconRes = R.drawable.ic_sports,
//                    color = Color.Red
//                ) {
//                    navController.navigate("playerMain")
//                }
//            }
//            item {
//                RoleButton(
//                    label = "Admin Login",
//                    iconRes = R.drawable.ic_admin,
//                    color = Color.Gray
//                ) {
//                    navController.navigate("adminMain")
//                }
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    HockeyTheme {
        LoginScreen(navController = NavController(LocalContext.current))
    }
}
