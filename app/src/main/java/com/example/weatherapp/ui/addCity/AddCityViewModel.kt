package com.example.weatherapp.ui.addCity

import android.app.Activity
import android.app.Application
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.db.MyDatabase
import com.example.weatherapp.db.city.City
import com.example.weatherapp.db.city.CityDao
import com.example.weatherapp.models.api.CityForecast
import com.example.weatherapp.models.enums.OrderOwnerEnum
import com.example.weatherapp.services.ForecastService
import com.example.weatherapp.services.ServiceBuilder
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCityViewModel(application: Application) : AndroidViewModel(application) {

    private val cityDao: CityDao
    private val primaryCity: LiveData<City?>
    var chosenCity = ""
    var isCityCorrect: Boolean? = false
    var isRequestDone: MutableLiveData<Boolean> = MutableLiveData()
    lateinit var orderOwner: OrderOwnerEnum

    init {
        val cityDb = MyDatabase.getDatabase(application)
        cityDao = cityDb!!.cityDao()
        primaryCity = cityDao.primaryCity
    }

    fun insertAsync(city: City) = runBlocking {
        async(Dispatchers.Default) {
            cityDao.insert(city)
        }
    }

    fun updateAsync(cityName: String) = runBlocking {
        async(Dispatchers.Default) {
            val city = cityDao.getOnePrimaryCity()!!
            val changedCity = City(
                id = city.id,
                name = cityName,
                isPrimary = city.isPrimary
            )
            cityDao.update(changedCity)
        }
    }

    fun canPressButton(cityName: String): Boolean {
        return cityName.isNotEmpty()
    }

    private fun fetchCity(name: String) {
        val taskService = ServiceBuilder().buildService(ForecastService::class.java)
        val parameters = HashMap<String, String>()
        parameters["q"] = name
        parameters["days"] = "3"
        parameters["lang"] = "pl"
        val call = taskService.getForecast(parameters)
        call.enqueue(object : Callback<CityForecast> {
            override fun onResponse(
                call: Call<CityForecast>,
                response: Response<CityForecast>
            ) {
                when (response.code()) {
                    200 -> {
                        chosenCity = response.body()!!.location.name
                        isCityCorrect = true
                    }

                    400 -> {
                        isCityCorrect = false
                    }
                    else -> {
                        isCityCorrect = false
                    }
                }
                isRequestDone.value = true
            }

            override fun onFailure(call: Call<CityForecast>, t: Throwable) {
                isCityCorrect = false
                isRequestDone.value = true
            }

        })
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus

        if (view == null) {
            view = View(activity)
        }

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun fetchPrimaryCity(): City? = runBlocking {
        val city = async(Dispatchers.Default) {
            cityDao.getOnePrimaryCity()
        }
        return@runBlocking city.await()
    }

    fun performAddCityButtonClick(givenCityName: String) {
        fetchCity(givenCityName)
    }

}