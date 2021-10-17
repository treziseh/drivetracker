package com.harrytrezise.drivetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: TripAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var tripList: RecyclerView

    private val savedTripViewModel: SavedTripViewModel by viewModels {
        SavedTripViewModelFactory((application as DriveTrackerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newTripButton = findViewById<FloatingActionButton>(R.id.newTrip)
        newTripButton.setOnClickListener {newTrip()}

        tripList = findViewById(R.id.tripList)

        linearLayoutManager = LinearLayoutManager(this)
        tripList.layoutManager = linearLayoutManager

        dbGet()
    }

    private fun newTrip() {
        val intent = Intent(this, NewTripActivity::class.java)
        intent.run {
            activityNewForResult.launch(this)
        }
    }

    private fun setAdapter(data: List<Trip>, tripList: RecyclerView) {
        adapter = TripAdapter(data, applicationContext) { showTripDetail(it) }
        tripList.adapter = adapter
    }

    private fun showTripDetail(trip: Trip) {
        val intent = Intent(this, TripDetailActivity::class.java)
        intent.run {
            this.putExtra("trip", trip)
            tripDetailForResult.launch(this)
        }
    }

    private val activityNewForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val returnData = result.data
            val trip = returnData?.getParcelableExtra<Trip>("returnTrip")
            trip?.let {
                savedTripViewModel.insert(convert(trip))
            }
            dbGet()
        }
    }

    private val tripDetailForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val returnData = result.data
            val trip = returnData?.getParcelableExtra<Trip>("returnTrip")
            val delete = returnData?.getBooleanExtra("deleteTrip", false)
            trip?.let {
                if (delete == true) {
                    savedTripViewModel.delete(convert(trip))
                } else {
                    savedTripViewModel.insert(convert(trip))
                }
            }
            dbGet()
        }
    }

    private fun convert(trip: Trip): SavedTrip {
        return SavedTrip(
            trip.id,
            trip.startTime.timeInMillis,
            trip.endTime?.timeInMillis,
            trip.odoStart,
            trip.odoEnd,
            trip.distance,
            trip.description
        )
    }

    private fun dbGet() {
        savedTripViewModel.allSavedTrips.observe(this) { savedTrips ->
            val loadedTrips = mutableListOf<Trip>()
            for (savedTrip in savedTrips) {
                val startCal = GregorianCalendar()
                startCal.timeInMillis = savedTrip.startTime
                var endCal: GregorianCalendar?
                endCal = null
                if (savedTrip.endTime != null) {
                    endCal = GregorianCalendar()
                    endCal.timeInMillis = savedTrip.endTime
                }
                val retrievedTrip = Trip(
                    savedTrip.trip_id,
                    startCal,
                    endCal,
                    savedTrip.odoStart,
                    savedTrip.odoEnd,
                    savedTrip.distance,
                    savedTrip.description
                )
                loadedTrips.add(retrievedTrip)
            }

            setAdapter(loadedTrips, tripList)
        }
    }
}