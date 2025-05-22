package com.hockey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.hockey.ui.screens.auth.LoginScreen
import com.hockey.ui.theme.HockeyTheme

// DO NOT CHANGE ANYTHING HERE
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HockeyTheme {
                //MainScreen()
                LoginScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HockeyTheme {
        Main1Screen()
    }
}
