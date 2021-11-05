package com.harrytrezise.drivetracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import java.util.*

class TripDetailActivity : AppCompatActivity() {
    private val shortMonths = arrayOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")

    private lateinit var detailTrip: Trip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_detail)

        supportActionBar?.title = getString(R.string.view_trip) // set action bar title

        detailTrip = intent.getParcelableExtra("trip")!!

        initTrip()
    }

    private fun initTrip() {
        val editButton = findViewById<Button>(R.id.editTrip)
        val deleteButton = findViewById<Button>(R.id.deleteTrip)

        editButton.setOnClickListener { editTrip() }
        deleteButton.setOnClickListener {
            returnTrip(true)
            finish()
        }

        val tripDay = findViewById<TextView>(R.id.tripDay)
        val tripMonth = findViewById<TextView>(R.id.tripMonth)
        val tripDistance = findViewById<TextView>(R.id.tripDistance)
        val tripDescription = findViewById<TextView>(R.id.tripDescription)
        val tripPeriod = findViewById<TextView>(R.id.tripPeriod)

        detailTrip.let {
            tripMonth.text = shortMonths[it.startTime.get(Calendar.MONTH)]
            tripDay.text = it.startTime.get(Calendar.DAY_OF_MONTH).toString()
            var displayDistance = "0 km"
            if (it.distance != null) {
                displayDistance = it.distance.toString() + " km"
            }
            tripDistance.text = displayDistance
            tripDescription.text = it.description

            val sHour = it.startTime.get(Calendar.HOUR_OF_DAY)
            val sMinute = it.startTime.get(Calendar.MINUTE)
            val sHourText = if (sHour < 10) "0$sHour" else sHour
            val sMinuteText = if (sMinute < 10) "0$sMinute" else sMinute
            val sTimeDisplay = "$sHourText:$sMinuteText"

            var eTimeDisplay = "N/A"
            if (it.endTime != null) {
                val eHour = it.endTime?.get(Calendar.HOUR_OF_DAY)
                val eMinute = it.endTime?.get(Calendar.MINUTE)
                val eHourText = if (eHour!! < 10) "0$eHour" else eHour
                val eMinuteText = if (eMinute!! < 10) "0$eMinute" else eMinute
                eTimeDisplay = "$eHourText:$eMinuteText"
            }

            val displayPeriod = "$sTimeDisplay - $eTimeDisplay"
            tripPeriod.text = displayPeriod
        }
    }

    override fun onBackPressed() {
        returnTrip(false)

        super.onBackPressed()
    }

    private fun returnTrip(deleteTrip: Boolean) {
        val returnIntent = intent.run {
            putExtra("returnTrip", detailTrip)
            putExtra("deleteTrip", deleteTrip)
        }
        setResult(Activity.RESULT_OK, returnIntent)
    }

    private fun editTrip() {
        val intent = Intent(this, EditTripActivity::class.java)
        intent.run {
            this.putExtra("editTrip", detailTrip)
            editTripForResult.launch(this)
        }
    }

    private val editTripForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val returnData = result.data
            val error = returnData?.getBooleanExtra("inputError", true)
            val trip = returnData?.getParcelableExtra<Trip>("returnTrip")
            if (error == false) {
                trip?.let {
                    detailTrip.id = it.id
                    detailTrip.startTime = it.startTime
                    detailTrip.endTime = it.endTime
                    detailTrip.odoStart = it.odoStart
                    detailTrip.odoEnd = it.odoEnd
                    detailTrip.distance = it.distance
                    detailTrip.description = it.description
                }
            } else {
                val errToast = Toast.makeText(applicationContext, "Input Errors, Trip Not Updated", Toast.LENGTH_SHORT)
                errToast.show()
            }
        }
        initTrip()
    }
}