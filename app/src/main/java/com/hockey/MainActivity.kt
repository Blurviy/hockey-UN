package com.hockey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.hockey.ui.navigation.AppNavigation
import com.hockey.ui.navigation.Main1Screen
import com.hockey.ui.theme.HockeyTheme

// DO NOT CHANGE ANYTHING HERE
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enables full-screen usage
        setContent {
            AppNavigation(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.nhu_logo),
            contentDescription = "Splash Screen Image",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HockeyTheme {
        Main1Screen()
    }
}
