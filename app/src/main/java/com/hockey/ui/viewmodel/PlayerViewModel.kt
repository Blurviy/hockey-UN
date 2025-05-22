// Create a new file: ui/viewmodel/PlayerViewModel.kt
package com.hockey.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hockey.data.entities.Player
import com.hockey.data.repository.PlayerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players: StateFlow<List<Player>> = _players.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadPlayersForTeam(teamId: Long) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                playerRepository.getPlayersForTeam(teamId).collect { playerList ->
                    _players.value = playerList
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addPlayer(
        teamId: Long,
        name: String,
        email: String,
        mobileNumber: String,
        birthCertificatePath: String? = null,
        passportPhotoPath: String? = null
    ) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val player = Player(
                    teamId = teamId,
                    name = name,
                    email = email,
                    mobileNumber = mobileNumber,
                    birthCertificatePath = birthCertificatePath,
                    passportPhotoPath = passportPhotoPath
                )
                playerRepository.insertPlayer(player)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updatePlayer(player: Player) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                playerRepository.updatePlayer(player)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deletePlayer(player: Player) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                playerRepository.deletePlayer(player)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}