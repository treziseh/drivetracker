package com.harrytrezise.drivetracker

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.*
import kotlinx.coroutines.*
import java.util.*

class SavedTripViewModel(private val repository: SavedTripRepository) : ViewModel() {

    val allSavedTrips: LiveData<List<SavedTrip>> = repository.tripsList.asLiveData()

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