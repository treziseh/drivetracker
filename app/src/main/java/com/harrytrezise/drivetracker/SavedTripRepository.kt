package com.harrytrezise.drivetracker

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import java.util.*

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