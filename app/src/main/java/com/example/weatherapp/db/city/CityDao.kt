package com.example.weatherapp.db.city

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CityDao {

    @Insert
    fun insert(city: City)

    @Query("SELECT * FROM city WHERE isPrimary=1")
    fun getOnePrimaryCity(): City?

    @get:Query("SELECT * FROM city WHERE isPrimary=1")
    val primaryCity: LiveData<City?>

    @Query("SELECT * FROM city")
    fun getAllCities(): List<City>

    @get:Query("SELECT * FROM city WHERE isPrimary != 1")
    val savedCitiesWithoutPrimary: LiveData<List<City?>>

    @Update
    fun update(city: City)
}