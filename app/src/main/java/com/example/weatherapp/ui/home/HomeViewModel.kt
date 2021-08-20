package com.example.weatherapp.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.weatherapp.db.MyDatabase
import com.example.weatherapp.db.city.City
import com.example.weatherapp.db.city.CityDao
import com.example.weatherapp.models.CityForecast
import com.example.weatherapp.services.ForecastService
import com.example.weatherapp.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val cityDao: CityDao
//    var allHourWeather: List<HourWeather> = mutableListOf()
//    var allDayWeather: List<DayWeather> = mutableListOf()
    val primaryCity: LiveData<City?>

    init {
        val cityDb = MyDatabase.getDatabase(application)
        cityDao = cityDb!!.cityDao()
        primaryCity = cityDao.primaryCity
    }


    fun fetchForecastData(cityName: String) {
        val taskService = ServiceBuilder().buildService(ForecastService::class.java)
        val call = taskService.getForecast(cityName, 3, "pl")
        call.enqueue(object : Callback<CityForecast> {
            override fun onResponse(call: Call<CityForecast>, response: Response<CityForecast>) {
                val forecast = response.body()
                val responseCode = response.code()
            }

            override fun onFailure(call: Call<CityForecast>, t: Throwable) {
                val cos = 0
            }

        })
    }

}