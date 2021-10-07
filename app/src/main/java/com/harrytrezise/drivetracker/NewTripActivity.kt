package com.harrytrezise.drivetracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class NewTripActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_new)

        supportActionBar?.title = getString(R.string.new_trip)

        val newID = intent.getIntExtra("newID", 0)

        val startTimeDisplay = findViewById<TextView>(R.id.startTime)

        val startTime = GregorianCalendar()
        val dateDisplay = SimpleDateFormat("k:m       d/M/y", Locale.getDefault()).format(startTime.time)
        startTimeDisplay.text = dateDisplay

        val startManual = findViewById<Button>(R.id.startManual)
        startManual.setOnClickListener { constructNewTrip(newID, startTime) }
    }

    private fun constructNewTrip(newID: Int, startTime: GregorianCalendar) {
        val startOdo = findViewById<TextInputEditText>(R.id.startOdo).text.toString().toInt()
        val trip = Trip(newID, startTime, null, startOdo, null, null, "")
        val returnIntent = intent.run {
            putExtra("returnTrip", trip)
        }
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }
}