// Create a new file: data/AppDatabase.kt
package com.hockey.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hockey.data.dao.PlayerDao
import com.hockey.data.dao.TeamDao
import com.hockey.data.dao.UserDao
import com.hockey.data.entities.Player
import com.hockey.data.entities.Team
import com.hockey.data.entities.User

@Database(
    entities = [Team::class, Player::class, User::class], // Add User here
    version = 2, // Increment version
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun teamDao(): TeamDao
    abstract fun playerDao(): PlayerDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "hockey_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}