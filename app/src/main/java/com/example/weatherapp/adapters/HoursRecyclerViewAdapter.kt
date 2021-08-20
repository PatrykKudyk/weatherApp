package com.example.weatherapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R


class HoursRecyclerViewAdapter(private val context: Context): RecyclerView.Adapter<HoursRecyclerViewAdapter.HoursViewHolder>() {

//    private var items: List<HourWeather> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.listitem_hour, parent, false)
        return HoursViewHolder(view)
    }

    override fun onBindViewHolder(holder: HoursViewHolder, position: Int) {
//        val weather = items[position]
        holder.initViews()
//        holder.setData(weather)
    }

    override fun getItemCount(): Int {
//        return items.size
        return 0
    }

//    fun setItems(items :List<HourWeather>){
//        this.items = items
//    }

    inner class HoursViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private lateinit var hourImage: ImageView
        private lateinit var hourTemperature: TextView
        private lateinit var hourHour: TextView

        fun initViews(){
            hourImage = itemView.findViewById(R.id.hourImage)
            hourTemperature = itemView.findViewById(R.id.hourTemperature)
            hourHour = itemView.findViewById(R.id.hourHour)
        }

//        fun setData(hour: HourWeather){
//            hourImage.setImageDrawable(hour.image)
//            hourTemperature.text = "${hour.temperature} °C"
//            hourHour.text = hour.hour.toString()
//        }
    }
}