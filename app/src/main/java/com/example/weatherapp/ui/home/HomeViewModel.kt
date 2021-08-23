package com.example.weatherapp.ui.home

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.adapters.HoursRecyclerViewAdapter
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.db.MyDatabase
import com.example.weatherapp.db.city.City
import com.example.weatherapp.db.city.CityDao
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
                val isExecuted = call.isExecuted
                val isCancelled = call.isCanceled
                val cos = 0
            }

        })
    }

    private fun fillFields(activity: Activity, binding: FragmentHomeBinding, forecast: CityForecast){
        activity.runOnUiThread {
            binding.textViewTemperature.text = "${forecast.current.temp_c.toInt()} °C"
            binding.textViewDescription.text = forecast.current.condition.text

            hoursRecyclerViewAdapter = HoursRecyclerViewAdapter(activity)
            binding.recyclerHours.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerHours.adapter = hoursRecyclerViewAdapter
            hoursRecyclerViewAdapter.setItems(forecast.forecast.forecastday[0].hour)

            val currentHourPosition = calculateCurrentHourPosition(forecast)
            (binding.recyclerHours.layoutManager as LinearLayoutManager).scrollToPosition(currentHourPosition)
        }
    }

    private fun calculateCurrentHourPosition(forecast: CityForecast): Int {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val date = LocalDateTime.parse(forecast.location.localtime, formatter)
        return date.hour
    }

}