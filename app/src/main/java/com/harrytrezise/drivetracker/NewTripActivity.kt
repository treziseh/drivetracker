package com.harrytrezise.drivetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.util.*

class NewTripActivity : AppCompatActivity() {
    private var trip: Trip? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_new)

        supportActionBar?.title = getString(R.string.new_trip)

        val startTimeDisplay = findViewById<TextView>(R.id.startTime)

        val startTime = Calendar.getInstance()
        startTimeDisplay.text = startTimeDisplay.toString()

    }
}