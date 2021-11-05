package com.harrytrezise.drivetracker

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class NewTripActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_new)

        supportActionBar?.title = getString(R.string.new_trip)

        val newID = intent.getIntExtra("newID", 0)

        val startTimeDisplay = findViewById<TextView>(R.id.nStartTime)
        val startDateDisplay = findViewById<TextView>(R.id.nStartDate)
        val startManual = findViewById<Button>(R.id.startManual)

        val startTime = GregorianCalendar()

        val hour = startTime.get(Calendar.HOUR_OF_DAY)
        val minute = startTime.get(Calendar.MINUTE)
        val hourText = if (hour < 10) "0$hour" else hour
        val minuteText = if (minute < 10) "0$minute" else minute
        val timeDisplay = "$hourText:$minuteText"
        val dateMills = startTime.timeInMillis
        val dateZone = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateMills), ZoneId.systemDefault())
        val dateDisplay = dateZone.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        startTimeDisplay.text = timeDisplay
        startDateDisplay.text = dateDisplay

        val odoInput = findViewById<TextInputEditText>(R.id.startOdo)
        val number = Regex("[0-9]+")
        odoInput.doAfterTextChanged {
            if (odoInput.text.toString() == "") {
                odoInput.error = "Odometer reading cannot be empty"
                startManual.isEnabled = false
            }
            else if (!odoInput.text.toString().matches(number)) {
                odoInput.error = "Odometer reading must be numeric"
                startManual.isEnabled = false
            } else {
                startManual.isEnabled = true
            }
        }

        startManual.isEnabled = false
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