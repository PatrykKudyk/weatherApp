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
import com.example.weatherapp.models.HourForecast
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class HoursRecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<HoursRecyclerViewAdapter.HoursViewHolder>() {

    private var items = ArrayList<HourForecast>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.listitem_hour, parent, false)
        return HoursViewHolder(view)
    }

    override fun onBindViewHolder(holder: HoursViewHolder, position: Int) {
        val weather = items[position]
        holder.initViews()
        holder.setData(weather)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(hourForecast: ArrayList<HourForecast>) {
        items = hourForecast
        notifyDataSetChanged()
    }


    inner class HoursViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var hourImage: ImageView
        private lateinit var hourTemperature: TextView
        private lateinit var hourHour: TextView

        fun initViews() {
            hourImage = itemView.findViewById(R.id.hourImage)
            hourTemperature = itemView.findViewById(R.id.hourTemperature)
            hourHour = itemView.findViewById(R.id.hourHour)
        }

        fun setData(hour: HourForecast) {
            hourImage.setImageDrawable(
                WeatherDrawablesHelper().getCorrectDrawable(
                    hour.is_day,
                    hour.condition.code,
                    context
                )
            )
            hourTemperature.text = "${hour.temp_c.toInt()} °C"


            val convertedDate = CalendarHelper().getDateTimeFromString(hour.time)
            hourHour.text = convertedDate.hour.toString()
        }
    }
}