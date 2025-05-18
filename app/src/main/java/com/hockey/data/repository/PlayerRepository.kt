// Create a new file: data/repository/PlayerRepository.kt
package com.hockey.data.repository

import com.hockey.data.dao.PlayerDao
import com.hockey.data.entities.Player
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlayerRepository @Inject constructor(private val playerDao: PlayerDao) {
    fun getPlayersForTeam(teamId: Long): Flow<List<Player>> = playerDao.getPlayersForTeam(teamId)

    suspend fun getPlayerById(playerId: Long): Player? = playerDao.getPlayerById(playerId)

    suspend fun insertPlayer(player: Player): Long = playerDao.insertPlayer(player)

    suspend fun updatePlayer(player: Player) = playerDao.updatePlayer(player)

    suspend fun deletePlayer(player: Player) = playerDao.deletePlayer(player)
}