package com.harrytrezise.drivetracker

import android.app.Application

class DriveTrackerApplication : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { SavedTripRepository(database.savedTripDao()) }
}