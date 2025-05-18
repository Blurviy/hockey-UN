// Create a new file: data/dao/TeamDao.kt
package com.hockey.data.dao

import androidx.room.*
import com.hockey.data.entities.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Insert
    suspend fun insertTeam(team: Team): Long

    @Update
    suspend fun updateTeam(team: Team)

    @Delete
    suspend fun deleteTeam(team: Team)

    @Query("SELECT * FROM teams ORDER BY name ASC")
    fun getAllTeams(): Flow<List<Team>>

    @Query("SELECT * FROM teams WHERE teamId = :teamId")
    suspend fun getTeamById(teamId: Long): Team?
}