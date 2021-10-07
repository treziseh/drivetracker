package com.harrytrezise.drivetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class NewTripActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_new)

        supportActionBar?.title = getString(R.string.new_trip)

        val trip = intent.getIntExtra("newID", 0)

        val startTimeDisplay = findViewById<TextView>(R.id.startTime)

        val startTime = GregorianCalendar()
        val dateDisplay = SimpleDateFormat("k:m       d/M/y", Locale.getDefault()).format(startTime.time)
        startTimeDisplay.text = dateDisplay

    }
}