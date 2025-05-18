// Create a new file: data/dao/PlayerDao.kt
package com.hockey.data.dao

import androidx.room.*
import com.hockey.data.entities.Player
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {
    @Insert
    suspend fun insertPlayer(player: Player): Long

    @Update
    suspend fun updatePlayer(player: Player)

    @Delete
    suspend fun deletePlayer(player: Player)

    @Query("SELECT * FROM players WHERE teamId = :teamId ORDER BY name ASC")
    fun getPlayersForTeam(teamId: Long): Flow<List<Player>>

    @Query("SELECT * FROM players WHERE playerId = :playerId")
    suspend fun getPlayerById(playerId: Long): Player?
}