package com.example.weatherapp.models

data class DayForecast(
    val maxtemp_c: Double,
    val maxtemp_f: Double,
    val mintemp_c: Double,
    val mintemp_f: Double,
    val avgtemp_c: Double,
    val avgtemp_f: Double,
    val maxwind_mph: Double,
    val maxwind_kph: Double,
    val avghumidity: Int,
    val daily_chance_of_rain: Int,
    val daily_chance_of_snow: Int,
    val condition: Condition
)
