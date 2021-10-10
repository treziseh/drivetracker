package com.harrytrezise.drivetracker

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class EditTripActivity : AppCompatActivity() {
    private lateinit var editTrip: Trip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_edit)
        editTrip = intent.getParcelableExtra("editTrip")!!

        supportActionBar?.title = getString(R.string.edit_trip)

        val startDateSelected = findViewById<TextInputEditText>(R.id.startDate)
        val startTimeSelected = findViewById<TextInputEditText>(R.id.startTime)
        val endDateSelected = findViewById<TextInputEditText>(R.id.endDate)
        val endTimeSelected = findViewById<TextInputEditText>(R.id.endTime)

        val distanceHeader = findViewById<TextView>(R.id.distanceHeader)
        val displayDistance = editTrip.distance.toString()
        val formatDistance = "Distance: $displayDistance km"
        distanceHeader.text = formatDistance

        val odometerStart = findViewById<TextInputEditText>(R.id.odometerStart)
        val odometerEnd = findViewById<TextInputEditText>(R.id.odometerEnd)
        val odoStart = editTrip.odoStart.toString()
        odometerStart.setText(odoStart)
        if (editTrip.odoEnd != null) {
            val odoEnd = editTrip.odoEnd.toString()
            odometerEnd.setText(odoEnd)
        } else {
            odometerEnd.setText(getString(R.string.not_set))
        }

        val description = findViewById<TextInputEditText>(R.id.description)
        val desc = editTrip.description
        description.setText(desc)

        val cDay = editTrip.startTime.get(Calendar.DAY_OF_MONTH)
        val cMonth = editTrip.startTime.get(Calendar.MONTH) + 1
        val cYear = editTrip.startTime.get(Calendar.YEAR)
        val dateDisplay = "$cDay/$cMonth/$cYear"
        startDateSelected.setText(dateDisplay)
        val cHour = editTrip.startTime.get(Calendar.HOUR)
        val cMinute = editTrip.startTime.get(Calendar.MINUTE)
        val timeDisplay = "$cHour:$cMinute"
        startTimeSelected.setText(timeDisplay)

        if (editTrip.endTime != null) {
            val dDay = editTrip.endTime?.get(Calendar.DAY_OF_MONTH)
            var dMonth = editTrip.endTime?.get(Calendar.MONTH)
            dMonth = dMonth?.plus(1)
            val dYear = editTrip.endTime?.get(Calendar.YEAR)
            val dateDisplayA = "$dDay/$dMonth/$dYear"
            endDateSelected.setText(dateDisplayA)
            val dHour = editTrip.endTime?.get(Calendar.HOUR)
            val dMinute = editTrip.endTime?.get(Calendar.MINUTE)
            val timeDisplayA = "$dHour:$dMinute"
            endTimeSelected.setText(timeDisplayA)
        } else {
            endDateSelected.setText(getString(R.string.not_set))
            endTimeSelected.setText(getString(R.string.not_set))
        }

        startDateSelected.setOnClickListener { showDatePicker(editTrip.startTime, 's') }
        endDateSelected.setOnClickListener { showDatePicker(editTrip.startTime, 'e') }
        startTimeSelected.setOnClickListener { showTimePicker(editTrip.startTime.get(Calendar.HOUR), editTrip.startTime.get(Calendar.MINUTE), 's') }
        endTimeSelected.setOnClickListener { showTimePicker(editTrip.endTime?.get(Calendar.HOUR), editTrip.endTime?.get(Calendar.MINUTE), 'e') }

    }

    override fun onBackPressed() {
        setValues()
        returnTrip()
        super.onBackPressed()
    }

    private fun setValues() {
        val newStartTime = GregorianCalendar.getInstance()
        val timeFormat = SimpleDateFormat("dd/MM/yyyy k:m", Locale.ENGLISH)
        val startTimeString = findViewById<TextInputEditText>(R.id.startDate).text.toString() + " " + findViewById<TextInputEditText>(R.id.startTime).text.toString()
        newStartTime.time = timeFormat.parse(startTimeString)!!
        editTrip.startTime = newStartTime as GregorianCalendar

        val newEndTime = GregorianCalendar.getInstance()
        val endTimeString = findViewById<TextInputEditText>(R.id.endDate).text.toString() + " " + findViewById<TextInputEditText>(R.id.endTime).text.toString()
        newEndTime.time = timeFormat.parse(endTimeString)!!
        editTrip.endTime = newEndTime as GregorianCalendar

        val odoStartInt = findViewById<TextInputEditText>(R.id.odometerStart).text.toString().toInt()
        val odoEndInt = findViewById<TextInputEditText>(R.id.odometerEnd).text.toString().toInt()

        val distance = odoStartInt - odoEndInt

        editTrip.odoStart = odoStartInt
        editTrip.odoEnd = odoEndInt
        editTrip.distance = distance
        editTrip.description = findViewById<TextInputEditText>(R.id.description).toString()
    }

    private fun returnTrip() {
        val returnIntent = intent.run {
            putExtra("returnTrip", editTrip)
        }
        setResult(Activity.RESULT_OK, returnIntent)
    }

    private fun showDatePicker(time: GregorianCalendar, field: Char) {
        val selectedDateInMillis = time.timeInMillis

        MaterialDatePicker.Builder.datePicker().setSelection(selectedDateInMillis).build().apply {
            addOnPositiveButtonClickListener { dateInMillis -> onDateSelected(dateInMillis, field) }
        }.show(supportFragmentManager, MaterialDatePicker::class.java.canonicalName)
    }

    private fun showTimePicker(hour: Int?, minute: Int?, field: Char) {
        var aHour = hour
        var aMinute = minute
        if (hour == null) {
            aHour = LocalDateTime.now().hour
            aMinute = LocalDateTime.now().minute
        }

        MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(aHour!!)
            .setMinute(aMinute!!)
            .build()
            .apply {
                addOnPositiveButtonClickListener { onTimeSelected(this.hour, this.minute, field) }
            }.show(supportFragmentManager, MaterialTimePicker::class.java.canonicalName)
    }

    private fun onDateSelected(dateMills: Long, field: Char) {
        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateMills), ZoneId.systemDefault())
        val dateAsFormattedText: String = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        if (field == 's') {
            findViewById<TextInputEditText>(R.id.startDate).setText(dateAsFormattedText)
        } else {
            findViewById<TextInputEditText>(R.id.endDate).setText(dateAsFormattedText)
        }

    }

    private fun onTimeSelected(hour: Int, minute: Int, field: Char) {
        val hourText = if (hour < 10) "0$hour" else hour
        val minuteText = if (minute < 10) "0$minute" else minute

        val display = "$hourText:$minuteText"
        if (field == 's') {
            findViewById<TextInputEditText>(R.id.startTime).setText(display)
        } else {
            findViewById<TextInputEditText>(R.id.endTime).setText(display)
        }
    }

}