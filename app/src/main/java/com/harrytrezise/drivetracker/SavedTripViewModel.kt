package com.harrytrezise.drivetracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.*
import kotlinx.coroutines.*
import java.util.*

class SavedTripViewModel(private val repository: SavedTripRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allSavedTrips: LiveData<List<SavedTrip>> = repository.allWords.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(savedTrip: SavedTrip) = viewModelScope.launch {
        repository.insert(savedTrip)
    }

    fun delete(savedTrip: SavedTrip) = viewModelScope.launch {
        repository.delete(savedTrip)
    }
}

class SavedTripViewModelFactory(private val repository: SavedTripRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedTripViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavedTripViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}