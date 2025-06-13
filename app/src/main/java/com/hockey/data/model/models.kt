

package com.hockey.data.model

data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val role: String = "user", // user, admin, manager
    val createdAt: Long = System.currentTimeMillis()
)

data class Team(
    val id: String = "",
    val name: String = "",
    val managerName: String = "",
    val contactNumber: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val players: List<String> = emptyList() // List of player IDs
)

data class Player(
    val id: String = "",
    val email: String = "",
    val mobileNumber: String = "",
    val teamId: String = "",
    val name: String = "",
    val position: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
