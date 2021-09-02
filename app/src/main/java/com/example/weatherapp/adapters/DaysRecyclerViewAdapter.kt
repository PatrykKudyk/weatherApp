package com.example.weatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.helpers.CalendarHelper
import com.example.weatherapp.helpers.WeatherDrawablesHelper
import com.example.weatherapp.models.api.ForecastDay

class DaysRecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<DaysRecyclerViewAdapter.DaysViewHolder>() {

    private var daysList = ArrayList<ForecastDay>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.listitem_day, parent, false)
        return DaysViewHolder(view)
    }

    override fun onBindViewHolder(holder: DaysViewHolder, position: Int) {
        val day = daysList[position]
        holder.initViews()
        holder.setData(day, context)
    }

    override fun getItemCount(): Int {
        return daysList.size
//        return 0
    }

    fun setItems(allDayWeather: ArrayList<ForecastDay>) {
        daysList = allDayWeather
    }

    inner class DaysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var dayName: TextView
        private lateinit var dayTemperature: TextView
        private lateinit var dayImage: ImageView


        fun initViews() {
            dayName = itemView.findViewById(R.id.dayName)
            dayTemperature = itemView.findViewById(R.id.dayTemperature)
            dayImage = itemView.findViewById(R.id.dayImage)
        }

        fun setData(dayWeather: ForecastDay, context: Context) {

            dayName.text = CalendarHelper().getDayOfWeekFromSDateInString(dayWeather.date)
            dayTemperature.text = "${dayWeather.day.avgtemp_c.toInt()} °C"
            dayImage.setImageDrawable(
                WeatherDrawablesHelper().getCorrectDrawable(
                    1,
                    dayWeather.day.condition.code,
                    context
                )
            )
        }
    }
}