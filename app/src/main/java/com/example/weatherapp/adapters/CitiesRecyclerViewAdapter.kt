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
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.helpers.CalendarHelper
import com.example.weatherapp.helpers.WeatherDrawablesHelper
import com.example.weatherapp.models.CityForecast
import com.example.weatherapp.models.CityForecastWithIndex
import com.example.weatherapp.models.enums.OrderOwnerEnum

class CitiesRecyclerViewAdapter(private val context: Context) :
    RecyclerView.Adapter<CitiesRecyclerViewAdapter.CitiesViewHolder>() {

    private var items = ArrayList<CityForecastWithIndex?>()
    private var ROW_NORMAL = 0
    private var ROW_ADD = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        return when (viewType){
            ROW_NORMAL -> {
                val view = LayoutInflater.from(context).inflate(R.layout.listitem_city_normal, parent, false)
                CitiesNormalViewHolder(view)
            }
            ROW_ADD -> {
                val view = LayoutInflater.from(context).inflate(R.layout.listitem_city_add, parent, false)
                CitiesAddViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(context).inflate(R.layout.listitem_city_normal, parent, false)
                CitiesNormalViewHolder(view)
            }
        }
    }


    override fun onBindViewHolder(
        holder: CitiesRecyclerViewAdapter.CitiesViewHolder,
        position: Int
    ) {
        holder.initViews()
        holder.setData(position)
    }

    override fun getItemViewType(position: Int): Int {
        return if (items[position] == null) {
            ROW_ADD
        } else {
            ROW_NORMAL
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    fun setItems(cities: ArrayList<CityForecastWithIndex?>) {
        items = cities
        notifyDataSetChanged()
    }


    open inner class CitiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun initViews() {

        }

        open fun setData(position: Int){

        }
    }


    inner class CitiesNormalViewHolder(itemView: View): CitiesViewHolder(itemView){

        private lateinit var cityConstraint: ConstraintLayout
        private lateinit var timeTextView: TextView
        private lateinit var cityNameTextView: TextView
        private lateinit var temperatureTextView: TextView
        private lateinit var weatherImage: ImageView

        override fun initViews() {
            cityConstraint = itemView.findViewById(R.id.cityConstraint)
            timeTextView = itemView.findViewById(R.id.timeTextView)
            cityNameTextView = itemView.findViewById(R.id.cityTextView)
            temperatureTextView = itemView.findViewById(R.id.temperatureTextView)
            weatherImage = itemView.findViewById(R.id.weatherImageView)
        }

        override fun setData(position: Int) {
            val currentItem = items[position]!!.forecast
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

    inner class CitiesAddViewHolder(itemView: View): CitiesViewHolder(itemView){
        private lateinit var addImage: ImageView

        override fun initViews() {
            addImage = itemView.findViewById(R.id.addImage)
        }

        override fun setData(position: Int) {
            addImage.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("order_owner", OrderOwnerEnum.CHOSEN_FRAGMENT)

                val navController = Navigation.findNavController(itemView)
                navController.navigate(R.id.action_nav_chosen_to_add_city, bundle)
            }
        }
    }
}