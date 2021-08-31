package com.example.weatherapp.ui.chosen

import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapters.CitiesRecyclerViewAdapter
import com.example.weatherapp.databinding.FragmentChosenBinding
import com.example.weatherapp.db.MyDatabase
import com.example.weatherapp.db.city.City
import com.example.weatherapp.db.city.CityDao
import com.example.weatherapp.models.CityForecast
import com.example.weatherapp.services.ForecastService
import com.example.weatherapp.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChosenViewModel(application: Application): AndroidViewModel(application) {

    private lateinit var citiesRecyclerViewAdapter: CitiesRecyclerViewAdapter
    private val fetchedCities = ArrayList<CityForecast?>()
    private val cityDao: CityDao
    val savedCities: LiveData<List<City?>>

    init {
        val cityDb = MyDatabase.getDatabase(application)
        cityDao = cityDb!!.cityDao()
        savedCities = cityDao.savedCitiesWithoutPrimary
    }

    fun initRecyclerView(activity: Activity, binding: FragmentChosenBinding) {
        fetchedCities.add(null)
        citiesRecyclerViewAdapter = CitiesRecyclerViewAdapter(activity)
        binding.citiesRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.citiesRecyclerView.adapter = citiesRecyclerViewAdapter
        citiesRecyclerViewAdapter.setItems(fetchedCities)
    }

    fun fetchCityData(city: City, activity: Activity) {
        val taskService = ServiceBuilder().buildService(ForecastService::class.java)
        val parameters = HashMap<String, String>()
        parameters["q"] = city.name
        parameters["days"] = "3"
        parameters["lang"] = "pl"
        val call = taskService.getForecast(parameters)
        call.enqueue(object : Callback<CityForecast> {
            override fun onResponse(call: Call<CityForecast>, response: Response<CityForecast>) {
                when (response.code()) {
                    200 -> addNewCity(response.body()!!)
                }
            }

            override fun onFailure(call: Call<CityForecast>, t: Throwable) {
                activity.runOnUiThread {
                    Toast.makeText(
                        activity.applicationContext,
                        activity.applicationContext.getText(
                            R.string.toast_something_went_wrong_try_again
                        ),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    private fun addNewCity(cityForecast: CityForecast) {
        fetchedCities.add(0, cityForecast)
        citiesRecyclerViewAdapter.setItems(fetchedCities)
    }

}