// data/entities/User.kt
package com.hockey.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0,
    val email: String,
    val password: String, // Note: In production, store hashed passwords!
    val name: String,
    val role: String = "user" // "admin" or "user"
)