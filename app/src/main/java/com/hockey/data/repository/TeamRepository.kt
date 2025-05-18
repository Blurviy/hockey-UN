// Create a new file: data/repository/TeamRepository.kt
package com.hockey.data.repository

import com.hockey.data.dao.TeamDao
import com.hockey.data.entities.Team
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TeamRepository @Inject constructor(private val teamDao: TeamDao) {
    fun getAllTeams(): Flow<List<Team>> = teamDao.getAllTeams()

    suspend fun getTeamById(teamId: Long): Team? = teamDao.getTeamById(teamId)

    suspend fun insertTeam(team: Team): Long = teamDao.insertTeam(team)

    suspend fun updateTeam(team: Team) = teamDao.updateTeam(team)

    suspend fun deleteTeam(team: Team) = teamDao.deleteTeam(team)
}