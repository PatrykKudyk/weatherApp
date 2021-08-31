package com.example.weatherapp.ui.home

import android.app.Activity
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.adapters.DaysRecyclerViewAdapter
import com.example.weatherapp.adapters.HoursRecyclerViewAdapter
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.db.MyDatabase
import com.example.weatherapp.db.city.City
import com.example.weatherapp.db.city.CityDao
import com.example.weatherapp.helpers.CalendarHelper
import com.example.weatherapp.helpers.WeatherDrawablesHelper
import com.example.weatherapp.models.CityForecast
import com.example.weatherapp.models.InformationField
import com.example.weatherapp.models.Mocks
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
    var shouldAnimate = true
    var areDetailsVisible = false

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
                when (response.code()) {
                    200 -> fillFields(activity, binding, response.body()!!)
                    else -> {
                        activity.runOnUiThread {
                            Toast.makeText(
                                activity.applicationContext,
                                activity.applicationContext.getText(
                                    R.string.toast_response_not_known
                                ),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
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

    private fun fillFields(
        activity: Activity,
        binding: FragmentHomeBinding,
        forecast: CityForecast
    ) {
        activity.runOnUiThread {
            if (forecast.forecast.forecastday.size == 0)
                forecast.forecast.forecastday = Mocks().getDaysWeatherList()

            hoursRecyclerViewAdapter = HoursRecyclerViewAdapter(activity)
            binding.recyclerHours.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            binding.recyclerHours.adapter = hoursRecyclerViewAdapter
            hoursRecyclerViewAdapter.setItems(forecast.forecast.forecastday[0].hour)
            binding.recyclerHours.isNestedScrollingEnabled = false

            val currentHourPosition = calculateCurrentHourPosition(forecast)
            (binding.recyclerHours.layoutManager as LinearLayoutManager).scrollToPosition(
                currentHourPosition
            )

            daysRecyclerViewAdapter = DaysRecyclerViewAdapter(activity)
            binding.recyclerDays.layoutManager = LinearLayoutManager(activity)
            binding.recyclerDays.adapter = daysRecyclerViewAdapter
            daysRecyclerViewAdapter.setItems(forecast.forecast.forecastday)
            binding.recyclerDays.isNestedScrollingEnabled = false

            binding.textViewTemperature.text =
                "${forecast.forecast.forecastday[0].hour[currentHourPosition].temp_c.toInt()} °C"
            binding.textViewDescription.text = forecast.current.condition.text
            binding.textViewHighTemperature.text =
                "H: " + forecast.forecast.forecastday[0].day.maxtemp_c.toInt().toString()
            binding.textViewLowTemperature.text =
                "L: " + forecast.forecast.forecastday[0].day.mintemp_c.toInt().toString()


            binding.detailsTable.createTable(
                arrayListOf(
                    InformationField(activity.getString(R.string.chance_of_rain).uppercase(), forecast.forecast.forecastday[0].day.daily_chance_of_rain.toString() + " %"),
                    InformationField(activity.getString(R.string.chance_of_snow).uppercase(), forecast.forecast.forecastday[0].day.daily_chance_of_snow.toString() + " %"),
                    InformationField(activity.getString(R.string.humidity).uppercase(), forecast.forecast.forecastday[0].day.avghumidity.toString() + " %"),
                    InformationField(activity.getString(R.string.feels_like).uppercase(), forecast.current.feelslike_c.toString() + " °C"),
                    InformationField(activity.getString(R.string.wind_dir).uppercase(), forecast.current.wind_dir),
                    InformationField(activity.getString(R.string.wind_speed).uppercase(), forecast.current.wind_kph.toString() + " km/h"),
                    InformationField(activity.getString(R.string.pressure).uppercase(), forecast.current.pressure_mb.toString() + " hPa"),
                    InformationField(activity.getString(R.string.clouds).uppercase(), forecast.current.cloud.toString() + " %"),
                    InformationField(activity.getString(R.string.sunrise).uppercase(), forecast.forecast.forecastday[0].astro.sunrise),
                    InformationField(activity.getString(R.string.sunset).uppercase(), forecast.forecast.forecastday[0].astro.sunset),
                    InformationField(activity.getString(R.string.moonrise).uppercase(), forecast.forecast.forecastday[0].astro.moonrise),
                    InformationField(activity.getString(R.string.moonset).uppercase(), forecast.forecast.forecastday[0].astro.moonset),
                    InformationField(activity.getString(R.string.moon_phase).uppercase(), forecast.forecast.forecastday[0].astro.moon_phase)
                )
            )
            binding.backgroundView.background = WeatherDrawablesHelper().getBackgroundDrawable(
                forecast.forecast.forecastday[0].hour[currentHourPosition].is_day,
                forecast.forecast.forecastday[0].day.condition.code,
                activity.applicationContext
            )
        }
    }

    private fun calculateCurrentHourPosition(forecast: CityForecast): Int {
        val convertedDate = CalendarHelper().getDateTimeFromString(forecast.location.localtime)
        return convertedDate.hour
    }

}