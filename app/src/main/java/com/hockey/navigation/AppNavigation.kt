package com.hockey.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hockey.ui.screens.auth.LoginScreen
import com.hockey.ui.screens.auth.SignupScreen
import com.hockey.ui.screens.events.EventDetailsScreen
import com.hockey.ui.screens.events.EventScreen
import com.hockey.ui.screens.events.events
import com.hockey.ui.screens.home.HomeScreen
import com.hockey.ui.screens.news.NewsAndUpdateScreen
import com.hockey.ui.screens.settings.SettingsScreen
import com.hockey.ui.screens.team.TeamManagementScreen
import com.hockey.ui.viewmodels.AuthViewModel

sealed class AppScreen(val route: String) {

    // Auth Screens
    object Login : AppScreen("login")
    object Signup : AppScreen("signup")

    // Role-Specific Home Screens
    object ManagerMain : AppScreen("manager_main")
    object PlayerMain : AppScreen("player_main")
    object FanMain : AppScreen("fan_main")
    object AdminMain : AppScreen("admin_main")
    object NoLoginMain : AppScreen("no_login")

    // Event Screens
    object EventDetails : AppScreen("event_details/{eventId}") {
        fun createRoute(eventId: String) = "event_details/$eventId"
    }

    object EventCreation : AppScreen("event_creation")

    // News Screens
    object NewsCreation : AppScreen("news_creation")
    object NewsAndUpdate : AppScreen("updates")

    // Team Screens
    // object TeamEdit : AppScreen("team_edit")
    object PlayerManagement : AppScreen("player_management") // Team ed
    object PlayerRegistration : AppScreen("player_registration")
    object TeamRegistration : AppScreen("team_registration")
    object TeamManagement : AppScreen("team_management")

    // Miscellaneous Screens
    object Messages : AppScreen("messages")
    object Settings : AppScreen("settings")
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreen.NoLoginMain.route
    ) {
        // Auth Screens
        composable(AppScreen.Login.route) {
            LoginScreen(modifier, navController, authViewModel)
        }
        composable(AppScreen.Signup.route) {
            SignupScreen(modifier, navController, authViewModel)
        }

        // Role-Specific Home Screens
        composable(AppScreen.NoLoginMain.route) {
            NavigationBar(role = "noLogin", navController = navController)
        }
        composable(AppScreen.ManagerMain.route) {
            NavigationBar(role = "manager", navController = navController)
        }
        composable(AppScreen.FanMain.route) {
            NavigationBar(role = "fan", navController = navController)
        }
        composable(AppScreen.PlayerMain.route) {
            NavigationBar(role = "player", navController = navController)
        }
        composable(AppScreen.AdminMain.route) {
            NavigationBar(role = "admin", navController = navController)
        }

        // Event List Screen
        composable("event_list") {
            EventScreen(
                userRole = "Manager", // Replace with a dynamic role if needed
                onEventClick = { event ->
                    navController.navigate("event_details/${event.id}")
                },
                onRegisterTeamClick = { /* Handle registration */ },
                onAddEventClick = { /* Handle adding events */ }
            )
        }

        // Event Details Screen
        composable(
            route = "event_details/{eventId}",
            arguments = listOf(navArgument("eventId") { type = NavType.StringType })
        ) { backStackEntry ->
            val eventId = backStackEntry.arguments?.getString("eventId")
            val event = events.find { it.id.toString() == eventId } // Use the events list to fetch details
            if (event != null) {
                EventDetailsScreen(
                    event = event,
                    userRole = "Manager",
                    onBackClick = { navController.popBackStack() }
                )
            }
        }


        composable(AppScreen.EventCreation.route) {
            // EventCreationScreen()
        }

        // Team Screens
        composable(AppScreen.TeamManagement.route) {
            TeamManagementScreen(modifier, navController)
        }
        composable(AppScreen.PlayerManagement.route) {
            // PlayerManagementScreen()
        }

        // News Screens
        composable(AppScreen.NewsAndUpdate.route) {
            NewsAndUpdateScreen()
        }
        composable(AppScreen.NewsCreation.route) {
            // NewsCreationScreen()
        }

        // Settings
        composable(AppScreen.Settings.route) {
            SettingsScreen()
        }

        // Messages
        composable(AppScreen.Messages.route) {
            // MessagesScreen()
        }
    }
}
