package com.harrytrezise.drivetracker

import android.app.Application

class DriveTrackerApplication : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { SavedTripRepository(database.savedTripDao()) }

}