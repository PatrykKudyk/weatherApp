package com.example.weatherapp.adapters

import android.content.Context
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.helpers.CalendarHelper
import com.example.weatherapp.helpers.WeatherDrawablesHelper
import com.example.weatherapp.models.CityForecast
import com.example.weatherapp.models.enums.OrderOwnerEnum

class CitiesRecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<CitiesRecyclerViewAdapter.CitiesViewHolder>() {

    private var items = ArrayList<CityForecast?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.listitem_city, parent, false)
        return CitiesViewHolder(view)
    }


    override fun onBindViewHolder(
        holder: CitiesRecyclerViewAdapter.CitiesViewHolder,
        position: Int
    ) {
        holder.initViews()
        holder.setData(position)
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun setItems(cities: ArrayList<CityForecast?>) {
        items = cities
        notifyDataSetChanged()
    }


    inner class CitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var addConstraint: ConstraintLayout
        private lateinit var cityConstraint: ConstraintLayout
        private lateinit var timeTextView: TextView
        private lateinit var cityNameTextView: TextView
        private lateinit var temperatureTextView: TextView
        private lateinit var weatherImage: ImageView
        private lateinit var addImage: ImageView

        fun initViews() {
            addConstraint = itemView.findViewById(R.id.addCityConstraint)
            cityConstraint = itemView.findViewById(R.id.cityConstraint)
            timeTextView = itemView.findViewById(R.id.timeTextView)
            cityNameTextView = itemView.findViewById(R.id.cityTextView)
            temperatureTextView = itemView.findViewById(R.id.temperatureTextView)
            addImage = itemView.findViewById(R.id.addImage)
            weatherImage = itemView.findViewById(R.id.weatherImageView)
        }

        fun setData(position: Int) {
            if (items[position] == null) {
                prepareAddItem()
            } else {
                prepareWeatherItem(position)
            }
        }

        private fun prepareAddItem() {
            addConstraint.visibility = View.VISIBLE
            cityConstraint.visibility = View.GONE
            addImage.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("order_owner", OrderOwnerEnum.CHOSEN_FRAGMENT)


                val navController = Navigation.findNavController(itemView)
                navController.navigate(R.id.action_nav_chosen_to_add_city, bundle)
            }
        }

        private fun prepareWeatherItem(position: Int) {
            addConstraint.visibility = View.GONE
            cityConstraint.visibility = View.VISIBLE

            val currentItem = items[position]!!
            val currentHourPosition = calculateCurrentHourPosition(currentItem)

            timeTextView.text = CalendarHelper().getTimeFromDate(currentItem.location.localtime)
            cityNameTextView.text = currentItem.location.name
            temperatureTextView.text =
                currentItem.forecast.forecastday[0].hour[currentHourPosition].temp_c.toString() + " °C"
            weatherImage.setImageDrawable(
                WeatherDrawablesHelper().getCorrectDrawable(
                    currentItem.forecast.forecastday[0].hour[currentHourPosition].is_day,
                    currentItem.forecast.forecastday[0].hour[currentHourPosition].condition.code,
                    context
                )
            )
            cityConstraint.background = WeatherDrawablesHelper().getBackgroundDrawable(
                currentItem.forecast.forecastday[0].hour[currentHourPosition].is_day,
                currentItem.forecast.forecastday[0].hour[currentHourPosition].condition.code,
                context
            )
        }

        private fun calculateCurrentHourPosition(forecast: CityForecast): Int {
            val convertedDate = CalendarHelper().getDateTimeFromString(forecast.location.localtime)
            return convertedDate.hour
        }
    }

}