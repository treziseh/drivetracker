package com.harrytrezise.drivetracker

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

class TripDetailActivity : AppCompatActivity() {
    private val shortMonths = arrayOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")
    //private var detailID = 0
    private lateinit var detailTrip: Trip

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_detail)

        supportActionBar?.title = getString(R.string.view_trip) // set action bar title

        detailTrip = intent.getParcelableExtra<Trip>("trip")!!

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
            val displayDistance = it.distance.toString() + " km"
            tripDistance.text = displayDistance
            tripDescription.text = it.description
            val displayPeriod = it.startTime.get(Calendar.HOUR).toString() + ":" + it.startTime.get(Calendar.MINUTE).toString() + " - " + it.endTime?.get(Calendar.HOUR).toString() + ":" + it.endTime?.get(Calendar.MINUTE).toString()
            tripPeriod.text = displayPeriod
            //detailID = it.id
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
            val trip = returnData?.getParcelableExtra<Trip>("returnTrip")
            trip?.let {
                detailTrip.id = it.id
                detailTrip.startTime = it.startTime
                detailTrip.endTime = it.endTime
                detailTrip.odoStart = it.odoStart
                detailTrip.odoEnd = it.odoEnd
                detailTrip.distance = it.distance
                detailTrip.description = it.description
            }
        }
        initTrip()
    }
}