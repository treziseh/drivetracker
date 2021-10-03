package com.harrytrezise.drivetracker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView

class TripAdapter(private val data: List<Trip>, private val context: Context, private val listener: (Trip) -> Unit) : RecyclerView.Adapter<TripAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.layout_trip_row, parent, false) as View
        return ViewHolder(view)
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private val tripMonth: TextView = v.findViewById(R.id.tripMonth)
        private val tripDay: TextView = v.findViewById(R.id.tripDay)
        private val tripDistance: TextView = v.findViewById(R.id.tripTotalDistance)
        private val tripStartTime: TextView = v.findViewById(R.id.tripStartTime)
        private val tripEndTime: TextView = v.findViewById(R.id.tripEndTime)

        fun bind(item: Trip) {
            tripMonth.text = item.startTime.month.toString()
            tripDay.text = item.startTime.day.toString()
            tripDistance.text = item.distance.toString()
            tripStartTime.text = item.startTime.toString()
            tripEndTime.text = item.endTime.toString()
            v.setOnClickListener { listener(item) }
        }
    }

    override fun onBindViewHolder(holder: TripAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size
}