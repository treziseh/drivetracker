package com.harrytrezise.drivetracker

import android.content.Context
import androidx.room.*

@Database(entities = [SavedTrip::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun savedTripDao(): SavedTripDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Return if not null, else create database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "saved_trip_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}