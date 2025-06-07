package com.hockey.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.hockey.ui.screens.auth.LoginScreen
import com.hockey.ui.screens.auth.SignupScreen
import com.hockey.ui.screens.events.AdminEventScreen
import com.hockey.ui.screens.events.EventCreationScreen
import com.hockey.ui.screens.events.EventDetailsScreen
import com.hockey.ui.screens.events.EventScreen
import com.hockey.ui.screens.events.FanEventScreen
import com.hockey.ui.screens.events.ManagerEventScreen
import com.hockey.ui.screens.events.PlayerEventScreen
import com.hockey.ui.screens.news.NewsAndUpdateScreen
import com.hockey.ui.screens.news.NewsCreationScreen
import com.hockey.ui.screens.settings.SettingsScreen
import com.hockey.ui.screens.team.ActiveTeamsScreen
import com.hockey.ui.screens.team.MessagesScreen
import com.hockey.ui.screens.team.PlayerManagementScreen
import com.hockey.ui.screens.team.PlayerRegistrationScreen
import com.hockey.ui.screens.team.TeamScreen
import com.hockey.ui.screens.team.TeamRegistrationScreen
import com.hockey.ui.screens.team.mockTeams
import com.hockey.ui.viewmodels.AuthViewModel
import com.hockey.utils.SampleEvents

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
    object FanEvent : AppScreen("fan_event")
    object ManagerEvent : AppScreen("manager_event")
    object PlayerEvent : AppScreen("player_event")
    object AdminEvent : AppScreen("admin_event")

    // News Screensa
    object NewsCreation : AppScreen("news_creation")
    object NewsAndUpdate : AppScreen("updates")

    // Team Screens
    // object TeamEdit : AppScreen("team_edit")
    object PlayerManagement : AppScreen("player_management") // Team ed
    object PlayerRegistration : AppScreen("player_registration")
    object TeamRegistration : AppScreen("team_registration")
    object Team : AppScreen("team")
    object ActiveTeams : AppScreen("active_teams")

    // Miscellaneous Screens
    object Messages : AppScreen("messages")
    object Settings : AppScreen("settings")
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreen.Login.route
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
        composable("event_list_noLogin") {
            EventScreen(
                role = "noLogin", // Replace with a dynamic role if needed
                onEventClick = { event ->
                    navController.navigate("event_details/${event.id}")
                },
                onRegisterTeamClick = { /* Handle registration */ },
                onAddEventClick = { /* Handle adding events */ },
                navController = navController
            )
        }
        // Event List Screen
        composable("event_list_manager") {
            EventScreen(
                role = "manager", // Replace with a dynamic role if needed
                onEventClick = { event ->
                    navController.navigate("event_details/${event.id}")
                },
                onRegisterTeamClick = { /* Handle registration */ },
                onAddEventClick = { /* Handle adding events */ },
                navController = navController
            )
        }
        // Event List Screen
        composable("event_list_fan") {
            EventScreen(
                role = "fan", // Replace with a dynamic role if needed
                onEventClick = { event ->
                    navController.navigate("event_details/${event.id}")
                },
                onRegisterTeamClick = { /* Handle registration */ },
                onAddEventClick = { /* Handle adding events */ },
                navController = navController
            )
        }
        // Event List Screen
        composable("event_list_player") {
            EventScreen(
                role = "player", // Replace with a dynamic role if needed
                onEventClick = { event ->
                    navController.navigate("event_details/${event.id}")
                },
                onRegisterTeamClick = { /* Handle registration */ },
                onAddEventClick = { /* Handle adding events */ },
                navController = navController
            )
        }
        // Event List Screen
        composable("event_list_admin") {
            EventScreen(
                role = "admin", // Replace with a dynamic role if needed
                onEventClick = { event ->
                    navController.navigate("event_details/${event.id}")
                },
                onRegisterTeamClick = { /* Handle registration */ },
                onAddEventClick = { /* Handle adding events */ },
                navController = navController
            )
        }

        // Event Details Screen
        composable(
            route = AppScreen.EventDetails.route,
            arguments = listOf(navArgument("eventId") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("eventId")?.let { eventId ->
                EventDetailsScreen(
                    event = SampleEvents().first { it.id == eventId },
                    userRole = "manager",
                    onBackClick = { navController.popBackStack() }
                )
            }
        }


        composable(AppScreen.EventCreation.route) {
             EventCreationScreen(modifier, navController)
        }

        // Event Screens (Added)
        composable(AppScreen.EventCreation.route) {
            EventCreationScreen(modifier, navController)
        }
        composable(AppScreen.FanEvent.route) {
            FanEventScreen(modifier, navController)
        }
        composable(AppScreen.ManagerEvent.route) {
            ManagerEventScreen(modifier, navController)
        }
        composable(AppScreen.PlayerEvent.route) {
            PlayerEventScreen(modifier, navController)
        }
        composable(AppScreen.AdminEvent.route) {
            AdminEventScreen(modifier, navController)
        }

        // Team Screens
        composable(AppScreen.Team.route) {
            TeamScreen(navController)
        }
        composable(AppScreen.PlayerManagement.route) {
             PlayerManagementScreen(modifier)
        }
        composable(AppScreen.PlayerRegistration.route) {
            PlayerRegistrationScreen(modifier)
        }
        composable(AppScreen.TeamRegistration.route) {
            TeamRegistrationScreen()
        }
        composable(AppScreen.ActiveTeams.route) {
            ActiveTeamsScreen(modifier = Modifier, teams = mockTeams,navController = navController)
        }

        // News Screens
        composable(AppScreen.NewsAndUpdate.route) {
            NewsAndUpdateScreen(modifier, navController)
        }
        composable(AppScreen.NewsCreation.route) {
             NewsCreationScreen(modifier, navController)
        }

        // Settings
        composable(AppScreen.Settings.route) {
            SettingsScreen(modifier, navController)
        }

        // Messages
        composable(AppScreen.Messages.route) {
            MessagesScreen(modifier, navController)
        }
    }
}

