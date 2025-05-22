package com.hockey.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.hockey.data.models.Player
import com.hockey.data.models.Team
import com.hockey.data.models.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")
    private val teamsRef = database.getReference("teams")
    private val playersRef = database.getReference("players")

    // Authentication Methods
    suspend fun signIn(email: String, password: String): Result<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw Exception("User ID is null")
            val userSnapshot = usersRef.child(userId).get().await()
            val user = userSnapshot.getValue(User::class.java)
                ?: throw Exception("User data not found")
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun signUp(email: String, password: String, name: String): Result<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: throw Exception("User ID is null")
            val user = User(id = userId, email = email, name = name)
            usersRef.child(userId).setValue(user).await()
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser(): User? {
        return auth.currentUser?.let { firebaseUser ->
            User(id = firebaseUser.uid, email = firebaseUser.email ?: "")
        }
    }

    // Team Methods
    suspend fun createTeam(team: Team): Result<String> {
        return try {
            val teamId = teamsRef.push().key ?: throw Exception("Failed to generate team ID")
            val teamWithId = team.copy(id = teamId)
            teamsRef.child(teamId).setValue(teamWithId).await()
            Result.success(teamId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getTeams(): Flow<List<Team>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val teams = snapshot.children.mapNotNull {
                    it.getValue(Team::class.java)
                }
                trySend(teams)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        teamsRef.addValueEventListener(listener)
        awaitClose { teamsRef.removeEventListener(listener) }
    }

    // Player Methods
    suspend fun createPlayer(player: Player): Result<String> {
        return try {
            val playerId = playersRef.push().key ?: throw Exception("Failed to generate player ID")
            val playerWithId = player.copy(id = playerId)
            playersRef.child(playerId).setValue(playerWithId).await()
            Result.success(playerId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getPlayers(): Flow<List<Player>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val players = snapshot.children.mapNotNull {
                    it.getValue(Player::class.java)
                }
                trySend(players)
            }
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }
        }
        playersRef.addValueEventListener(listener)
        awaitClose { playersRef.removeEventListener(listener) }
    }

    suspend fun deletePlayer(playerId: String): Result<Unit> {
        return try {
            playersRef.child(playerId).removeValue().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updatePlayer(player: Player): Result<Unit> {
        return try {
            playersRef.child(player.id).setValue(player).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}