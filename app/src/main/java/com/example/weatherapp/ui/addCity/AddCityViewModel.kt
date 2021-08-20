package com.example.weatherapp.ui.addCity

import android.app.Activity
import android.app.Application
import android.os.AsyncTask
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.db.MyDatabase
import com.example.weatherapp.db.city.City
import com.example.weatherapp.db.city.CityDao
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class AddCityViewModel(application: Application) : AndroidViewModel(application) {

    private val cityDao: CityDao
    val primaryCity: LiveData<City?>

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
                country = city.country,
                isPrimary = city.isPrimary
            )
            cityDao.update(changedCity)
        }
    }

    fun canPressButton(cityName: String): Int {
        return if (cityName.isNotEmpty()){
            if (doesCityExist()){
                CITY_EXISTS
            } else {
                CITY_DOES_NOT_EXIST
            }
        } else {
            NO_CITY_GIVEN
        }
    }

    private fun doesCityExist(): Boolean {

        // TODO zrobić strzał do api czy miasto istnieje
        return true
    }

    fun hideKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus

        if (view == null) {
            view = View(activity)
        }

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun fetchPrimaryCity(): City? = runBlocking{
        val city = async(Dispatchers.Default) {
            cityDao.getOnePrimaryCity()
        }
        return@runBlocking city.await()
    }

    companion object {
        const val NO_CITY_GIVEN = 1
        const val CITY_EXISTS = 2
        const val CITY_DOES_NOT_EXIST = 3
    }

}