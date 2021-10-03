package com.harrytrezise.drivetracker

import java.util.Date
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trip(var id: Int, var startTime: Date, var endTime: Date, var odoStart: Int, var odoEnd: Int, var distance: Int, var description: String) : Parcelable
