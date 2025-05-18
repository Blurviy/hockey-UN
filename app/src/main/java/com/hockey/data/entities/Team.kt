// Create a new file: data/entities/Team.kt
package com.hockey.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teams")
data class Team(
    @PrimaryKey(autoGenerate = true)
    val teamId: Long = 0,
    val name: String,
    val managerName: String,
    val contactNumber: String,
    val createdAt: Long = System.currentTimeMillis()
)