package com.example.weatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R

class DaysRecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<DaysRecyclerViewAdapter.DaysViewHolder>() {

//    private var daysList: List<DayWeather> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.listitem_day, parent, false)
        return DaysViewHolder(view)
    }

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
//        val day = daysList[position]
        holder.initViews()
//        holder.setData(day)
    }

    override fun getItemCount(): Int {
//        return daysList.size
        return 0
    }

//    fun setItems(allDayWeather: List<DayWeather>) {
//        daysList = allDayWeather
//    }

    inner class DaysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var dayName: TextView
        private lateinit var dayTemperature: TextView
        private lateinit var dayImage: ImageView


        fun initViews() {
            dayName = itemView.findViewById(R.id.dayName)
            dayTemperature = itemView.findViewById(R.id.dayTemperature)
            dayImage = itemView.findViewById(R.id.dayImage)
        }

//        fun setData(dayWeather: DayWeather) {
//            dayName.text = dayWeather.dayName
//            dayTemperature.text = "${dayWeather.temperature} °C"
//            dayImage.setImageDrawable(dayWeather.image)
//        }
    }
}