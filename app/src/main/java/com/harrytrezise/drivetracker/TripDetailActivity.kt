package com.harrytrezise.drivetracker

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import java.util.*

class TripDetailActivity : AppCompatActivity() {
    private var trip: Trip? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_detail)

        trip = intent.getParcelableExtra("trip")

        val tripDistance = findViewById<TextView>(R.id.tripTotalDistance)

        trip?.let {
            tripDistance.setText(it.distance.toString())
        }
    }
}