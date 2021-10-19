package com.harrytrezise.drivetracker

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedTripDao {
    @Query("SELECT * FROM SavedTrip")
    fun retrieveSaved(): Flow<MutableList<SavedTrip>>

    //@Query("SELECT SUM(distance) FROM SavedTrip WHERE (distance IS NOT NULL) AND (distance >= :minDate)")
    //suspend fun savedSum(minDate: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateSaved(vararg savedTrip: SavedTrip)

    @Delete
    suspend fun delete(vararg savedTrip: SavedTrip)
}