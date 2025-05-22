package com.hockey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hockey.ui.screens.EventScreen
import com.hockey.ui.screens.HomeScreen
import com.hockey.ui.screens.NewsAndUpdateScreen
import com.hockey.ui.screens.PlayerManagementScreen
import com.hockey.ui.screens.SettingsScreen
import com.hockey.ui.screens.SignupScreen
import com.hockey.ui.screens.TeamRegistrationScreen
import com.hockey.ui.theme.HockeyTheme
import android.app.Application
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.hockey.ui.screens.LoginScreen
import com.hockey.ui.viewmodel.AuthViewModel
import dagger.hilt.android.HiltAndroidApp
// DO NOT CHANGE ANYTHING HERE
@HiltAndroidApp
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HockeyTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = hiltViewModel()
                val currentUser by authViewModel.currentUser.collectAsState()

                NavHost(
                    navController = navController,
                    startDestination = if (currentUser != null) "main" else "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = { navController.navigate("main") },
                            onNavigateToSignup = { navController.navigate("signup") }
                        )
                    }
                    composable("signup") {
                        SignupScreen(
                            onSignupSuccess = { navController.navigate("main") },
                            onNavigateToLogin = { navController.navigate("login") }
                        )
                    }
                    composable("main") {
                        MainScreen(
                            onLogout = {
                                authViewModel.logout()
                                navController.navigate("login") {
                                    popUpTo("main") { inclusive = true }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
