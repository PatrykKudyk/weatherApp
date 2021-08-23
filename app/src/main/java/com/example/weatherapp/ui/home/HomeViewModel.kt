package com.example.weatherapp.ui.home

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.DaysRecyclerViewAdapter
import com.example.weatherapp.adapters.HoursRecyclerViewAdapter
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.db.MyDatabase
import com.example.weatherapp.db.city.City
import com.example.weatherapp.db.city.CityDao
import com.example.weatherapp.helpers.CalendarHelper
import com.example.weatherapp.models.CityForecast
import com.example.weatherapp.services.ForecastService
import com.example.weatherapp.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val cityDao: CityDao
    private lateinit var hoursRecyclerViewAdapter: HoursRecyclerViewAdapter
    private lateinit var daysRecyclerViewAdapter: DaysRecyclerViewAdapter
    val primaryCity: LiveData<City?>

    init {
        val cityDb = MyDatabase.getDatabase(application)
        cityDao = cityDb!!.cityDao()
        primaryCity = cityDao.primaryCity
    }


    fun fetchForecastData(cityName: String, activity: Activity, binding: FragmentHomeBinding) {
        val taskService = ServiceBuilder().buildService(ForecastService::class.java)
        val parameters = HashMap<String, String>()
        parameters["q"] = cityName
        parameters["days"] = "3"
        parameters["lang"] = "pl"
        val call = taskService.getForecast(parameters)
        call.enqueue(object : Callback<CityForecast> {
            override fun onResponse(call: Call<CityForecast>, response: Response<CityForecast>) {
                fillFields(activity, binding, response.body()!!)
            }

            override fun onFailure(call: Call<CityForecast>, t: Throwable) {

            }

        })
    }

    private fun fillFields(
        activity: Activity,
        binding: FragmentHomeBinding,
        forecast: CityForecast
    ) {
        activity.runOnUiThread {
            hoursRecyclerViewAdapter = HoursRecyclerViewAdapter(activity)
            binding.recyclerHours.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerHours.adapter = hoursRecyclerViewAdapter
            hoursRecyclerViewAdapter.setItems(forecast.forecast.forecastday[0].hour)

            val currentHourPosition = calculateCurrentHourPosition(forecast)
            (binding.recyclerHours.layoutManager as LinearLayoutManager).scrollToPosition(
                currentHourPosition
            )

            daysRecyclerViewAdapter = DaysRecyclerViewAdapter(activity)
            binding.recyclerDays.layoutManager = LinearLayoutManager(activity)
            binding.recyclerDays.adapter = daysRecyclerViewAdapter
            daysRecyclerViewAdapter.setItems(forecast.forecast.forecastday)

            binding.textViewTemperature.text =
                "${forecast.forecast.forecastday[0].hour[currentHourPosition].temp_c.toInt()} °C"
            binding.textViewDescription.text = forecast.current.condition.text
            binding.textViewHighTemperature.text =
                "H: " + forecast.forecast.forecastday[0].day.maxtemp_c.toInt().toString()
            binding.textViewLowTemperature.text =
                "L: " + forecast.forecast.forecastday[0].day.mintemp_c.toInt().toString()

        }
    }

    private fun calculateCurrentHourPosition(forecast: CityForecast): Int {
        val convertedDate = CalendarHelper().getDateTimeFromString(forecast.location.localtime)
        return convertedDate.hour
    }

}