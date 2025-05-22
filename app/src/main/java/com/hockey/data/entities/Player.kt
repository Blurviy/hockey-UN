// Updated file: data/entities/Player.kt
package com.hockey.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "players",
    foreignKeys = [ForeignKey(
        entity = Team::class,
        parentColumns = arrayOf("teamId"),
        childColumns = arrayOf("teamId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["teamId"])] // Add index for better performance
)
data class Player(
    @PrimaryKey(autoGenerate = true)
    val playerId: Long = 0,
    val teamId: Long, // Foreign key to Team
    val name: String,
    val email: String,
    val mobileNumber: String,
    val birthCertificatePath: String? = null,
    val passportPhotoPath: String? = null,
    val createdAt: Long = System.currentTimeMillis()
)