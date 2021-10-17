package com.harrytrezise.drivetracker

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class SavedTripRepository(private val savedTripDao: SavedTripDao) {
    val allWords: Flow<List<SavedTrip>> = savedTripDao.retrieveSaved()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(savedTrip: SavedTrip) {
        savedTripDao.updateSaved(savedTrip)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(savedTrip: SavedTrip) {
        savedTripDao.delete(savedTrip)
    }
}