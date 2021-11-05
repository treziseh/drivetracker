package com.harrytrezise.drivetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class TripAdapter(private var data: List<Trip>, private val listener: (Trip) -> Unit) : RecyclerView.Adapter<TripAdapter.ViewHolder>() {
    private val shortMonths = arrayOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC")

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
            tripMonth.text = shortMonths[item.startTime.get(Calendar.MONTH)]
            tripDay.text = item.startTime.get(Calendar.DAY_OF_MONTH).toString()
            val distanceDisplay = if (item.distance.toString() != "null") item.distance.toString() + " km" else "N/A"
            tripDistance.text = distanceDisplay
            val sHour = item.startTime.get(Calendar.HOUR_OF_DAY)
            val sMinute = item.startTime.get(Calendar.MINUTE)
            val sHourText = if (sHour < 10) "0$sHour" else sHour
            val sMinuteText = if (sMinute < 10) "0$sMinute" else sMinute
            val displayStart = "$sHourText:$sMinuteText"
            var displayEnd = "N/A"
            if (item.endTime != null) {
                val eHour = item.endTime?.get(Calendar.HOUR_OF_DAY)
                val eMinute = item.endTime?.get(Calendar.MINUTE)
                val eHourText = if (eHour!! < 10) "0$eHour" else eHour
                val eMinuteText = if (eMinute!! < 10) "0$eMinute" else eMinute
                displayEnd = "$eHourText:$eMinuteText"
            }
            tripStartTime.text = displayStart
            tripEndTime.text = displayEnd
            v.setOnClickListener { listener(item) }
        }
    }

    override fun onBindViewHolder(holder: TripAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size
}