package com.hockey.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.hockey.ui.screens.events.Event

object AppUtil {
    fun showToast(context: Context, message: String) {
Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }
}

@Composable
fun AppDropDown(
    menuItems: List<Triple<String, Any?, () -> Unit>> // Label, Icon (ImageVector or Painter), Action
) {
    var expanded by remember { mutableStateOf(false) } // State to control dropdown visibility

    if (menuItems.isEmpty()) {
        Text("No options available", modifier = Modifier.padding(8.dp))
        return
    }

    Box {
        // Trigger Icon
        Icon(
            imageVector = Icons.Filled.MoreVert,
            contentDescription = "More options",
            modifier = Modifier
                .size(24.dp)
                .clickable { expanded = true }
        )

        // Dropdown Menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            menuItems.forEach { (label, icon, action) ->
                DropdownMenuItem(
                    onClick = {
                        action()
                        expanded = false
                    },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            // Render either ImageVector or Painter
                            when (icon) {
                                is ImageVector -> Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                is Painter -> Icon(
                                    painter = icon,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                null -> Spacer(modifier = Modifier.size(20.dp)) // Placeholder for no icon
                                else -> throw IllegalArgumentException("Unsupported icon type")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = label)
                        }
                    }
                )
            }


        }
    }
}

@Composable
fun SampleEvents(): List<Event> {
    return listOf(
        Event(1, "Hockey Match: Team A vs Team B", "March 15, 2025", "5:00 PM", "Main Arena"),
        Event(2, "Training Camp", "March 20, 2025", "8:00 AM", "Training Center"),
        Event(3, "Fan Meet & Greet", "March 25, 2025", "7:00 PM", "Community Hall"),
        Event(4, "Championship Final", "April 1, 2025", "8:00 AM", "National Stadium")
    )
}

@Composable
fun FilteredEvents(
    events: List<Event>,
    filter: String
): List<Event> {
    return remember(filter) {
        when (filter) {
            "Unread" -> events.filter { !it.isActive }
            "Favorites" -> events /*TODO add favourite logic*/
            "Groups" -> events /*TODO add Groups logic*/
            else -> events
        }
    }
}

