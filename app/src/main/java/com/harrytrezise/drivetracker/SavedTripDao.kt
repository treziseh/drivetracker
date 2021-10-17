package com.harrytrezise.drivetracker

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedTripDao {
    @Query("SELECT * FROM SavedTrip")
    fun retrieveSaved(): Flow<MutableList<SavedTrip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSaved(vararg savedTrip: SavedTrip)

    @Delete
    suspend fun delete(vararg savedTrip: SavedTrip)
}