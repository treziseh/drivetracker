package com.harrytrezise.drivetracker

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class EditTripActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_edit)
        val trip = intent.getParcelableExtra<Trip>("editTrip")!!

        supportActionBar?.title = getString(R.string.edit_trip)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}