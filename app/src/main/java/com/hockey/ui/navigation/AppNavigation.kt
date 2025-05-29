package com.hockey.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.hockey.ui.screens.auth.AuthScreen
import com.hockey.ui.screens.auth.LoginScreen
import com.hockey.ui.screens.auth.SignupScreen
import com.hockey.ui.screens.events.EventScreen
import com.hockey.ui.screens.home.HomeScreen
import com.hockey.ui.screens.news.NewsAndUpdateScreen
import com.hockey.ui.screens.settings.SettingsScreen
import com.hockey.ui.screens.team.TeamManagementScreen
import com.hockey.ui.viewmodels.AuthViewModel

@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login") {
            LoginScreen(modifier, navController, authViewModel)
        }
        composable("signup") {
            SignupScreen(modifier, navController, authViewModel)
        }
        composable("home") {
            HomeScreen(modifier, navController, authViewModel)
        }
        composable("events") { EventScreen() }
        composable("team") { TeamManagementScreen(LocalContext.current) }
        composable("updates") { NewsAndUpdateScreen() }
        composable("settings") { SettingsScreen() }
    })
}


