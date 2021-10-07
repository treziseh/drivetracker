package com.harrytrezise.drivetracker

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class TripDetailActivity : AppCompatActivity() {
    val shortMonths = arrayOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_detail)

        supportActionBar?.title = getString(R.string.view_trip) // set action bar title

        val trip = intent.getParcelableExtra<Trip>("trip")

        val tripDay = findViewById<TextView>(R.id.tripDay)
        val tripMonth = findViewById<TextView>(R.id.tripMonth)
        val tripDistance = findViewById<TextView>(R.id.tripDistance)
        val tripDescription = findViewById<TextView>(R.id.tripDescription)
        val tripPeriod = findViewById<TextView>(R.id.tripPeriod)

        trip?.let {
            tripMonth.text = shortMonths[it.startTime.get(Calendar.MONTH)]
            tripDay.text = it.startTime.get(Calendar.DAY_OF_MONTH).toString()
            val displayDistance = it.distance.toString() + " km"
            tripDistance.text = displayDistance
            tripDescription.text = it.description
            val displayPeriod = it.startTime.get(Calendar.HOUR).toString() + ":" + it.startTime.get(Calendar.MINUTE).toString() + " - " + it.endTime.get(Calendar.HOUR).toString() + ":" + it.endTime.get(Calendar.MINUTE).toString()
            tripPeriod.text = displayPeriod
        }
    }

}