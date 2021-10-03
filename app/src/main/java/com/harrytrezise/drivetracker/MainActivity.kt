package com.harrytrezise.drivetracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
        intent.run {
            //putExtra()
            activityForResult.launch(this)
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
        intent.putExtra("trip", trip)
        startActivity(intent)
    }

    private val activityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val trip = data?.getParcelableExtra<Trip>("returnTrip")
        }
    }

    private fun initData(): List<Trip> {
        val data = mutableListOf<Trip>()
        data.add(Trip(1, Date(2021, 1, 13, 10, 40), Date(2021, 1, 13, 10, 50), 12000, 12015, 15, "Hamburger"))

        return data
    }
}