package com.harrytrezise.drivetracker

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class Trip(var id: Int, var startTime: GregorianCalendar, var endTime: GregorianCalendar, var odoStart: Int, var odoEnd: Int, var distance: Int, var description: String) : Parcelable
