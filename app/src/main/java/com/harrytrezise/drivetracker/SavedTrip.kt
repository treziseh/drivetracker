package com.harrytrezise.drivetracker

import androidx.room.*
import java.util.*

@Entity
data class SavedTrip (
    @PrimaryKey(autoGenerate = true) val tripId: Int,
    @ColumnInfo(name = "start_time") val startTime: Long,
    @ColumnInfo(name = "end_time") val endTime: Long?,
    @ColumnInfo(name = "odo_start") val odoStart: Int,
    @ColumnInfo(name = "odo_end") val odoEnd: Int?,
    @ColumnInfo(name = "distance") val distance: Int?,
    @ColumnInfo(name = "description") val description: String
)