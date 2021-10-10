package com.harrytrezise.drivetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: TripAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var tripList: RecyclerView

    private var data = initData()

    private fun newTrip() {
        val intent = Intent(this, NewTripActivity::class.java)
        var newID = 0
        if (data.isNotEmpty()) {
            newID = data.last().id + 1
        }
        intent.run {
            this.putExtra("newID", newID)
            activityNewForResult.launch(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newTripButton = findViewById<FloatingActionButton>(R.id.newTrip)
        newTripButton.setOnClickListener {newTrip()}

        tripList = findViewById<RecyclerView>(R.id.tripList)

        linearLayoutManager = LinearLayoutManager(this)
        tripList.layoutManager = linearLayoutManager

        setAdapter(data, tripList)
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
                data = data + trip
            }
            setAdapter(data, tripList)
        }
    }

    private val tripDetailForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val returnData = result.data
            val trip = returnData?.getParcelableExtra<Trip>("returnTrip")
            val delete = returnData?.getBooleanExtra("deleteTrip", false)
            trip?.let {
                if (delete == true) {
                    data = data - data[it.id]
                    updateIDs()
                } else {
                    data[it.id].id = it.id
                    data[it.id].startTime = it.startTime
                    data[it.id].endTime = it.endTime
                    data[it.id].odoStart = it.odoStart
                    data[it.id].odoEnd = it.odoEnd
                    data[it.id].distance = it.distance
                    data[it.id].description = it.description
                }
            }
            setAdapter(data, tripList)
        }
    }

    private fun updateIDs() {
        val length = data.size
        var i = 0
        while (i < length) {
            data[i].id = i
            i += 1
        }
    }

    private fun initData(): List<Trip> {
        val data = mutableListOf<Trip>()
        data.add(Trip(0, GregorianCalendar(2021, 0, 2, 10, 40), GregorianCalendar(2021, 0, 2, 10, 50), 12000, 12015, 15, "Hamburger"))
        data.add(Trip(1, GregorianCalendar(2020, 0, 2, 10, 40), GregorianCalendar(2021, 0, 2, 10, 50), 12000, 12015, 15, "Hamburger"))
        return data
    }
}