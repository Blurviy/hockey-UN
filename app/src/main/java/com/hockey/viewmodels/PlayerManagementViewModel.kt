package com.hockey.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.hockey.R
import com.hockey.ui.screens.team.Athlete

// ViewModel to manage athletes list
class PlayerManagementViewModel : ViewModel() {
    private val _athletes = mutableStateListOf(
        Athlete(
            1,
            "John Smith",
            "Forward",
            "23 Goals",
            R.drawable.john_smith,
            "john.smith@example.com"
        ),
        Athlete(
            2,
            "Sarah Johnson",
            "Midfielder",
            "15 Assists",
            R.drawable.sarah_johnson,
            "sarah.johnson@example.com"
        ),
        Athlete(
            3,
            "Mike Wilson",
            "Defender",
            "5 Clean Sheets",
            R.drawable.mike_wilson,
            "mike.wilson@example.com"
        )
    )
    val athletes: List<Athlete> get() = _athletes

    fun updateAthlete(updatedAthlete: Athlete) {
        val index = _athletes.indexOfFirst { it.id == updatedAthlete.id }
        if (index != -1) {
            _athletes[index] = updatedAthlete
        }
    }

    fun deleteAthlete(athlete: Athlete) {
        _athletes.removeIf { it.id == athlete.id }
    }

    fun addAthlete(name: String, position: String, stats: String, email: String, avatarResId: Int) {
        val newId = (_athletes.maxOfOrNull { it.id } ?: 0) + 1
        val newAthlete = Athlete(newId, name, position, stats, avatarResId, email)
        _athletes.add(newAthlete)
    }
}