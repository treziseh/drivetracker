package com.harrytrezise.drivetracker

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class SavedTripRepository(private val savedTripDao: SavedTripDao) {
    val tripsList: Flow<List<SavedTrip>> = savedTripDao.retrieveSaved()

    @WorkerThread
    suspend fun insert(savedTrip: SavedTrip) {
        savedTripDao.updateSaved(savedTrip)
    }

    @WorkerThread
    suspend fun delete(savedTrip: SavedTrip) {
        savedTripDao.delete(savedTrip)
    }
}