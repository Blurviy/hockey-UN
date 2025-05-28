package com.hockey.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import com.hockey.ui.screens.auth.AuthScreen
import com.hockey.ui.screens.auth.LoginScreen
import com.hockey.ui.screens.auth.SignupScreen
import com.hockey.ui.screens.home.HomeScreen

@Composable
fun AppNavigation(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("signup") {
            SignupScreen(navController)
        }
        composable("home") {
            HomeScreen()
        }
        composable("manager_home") {
            ManagerHomeScreen(navController, authViewModel)
        }
        composable("player_home") {
            PlayerHomeScreen(navController, authViewModel)
        }
        composable("admin_home") {
            AdminHomeScreen(navController, authViewModel)
        }
        composable("fan_home") {
            FanHomeScreen(navController, authViewModel)
        }
        composable("event_details/{eventId}") { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            EventDetailsScreen(eventId)
        }
        composable("active_teams") {
            ActiveTeamsScreen()
        }
        composable("add_update") {
            AddUpdateScreen()
        }
        composable("add_event") {
            AddEventScreen()
        }
        composable("direct_messages") {
            DirectMessagesScreen()
        }
    }
}


