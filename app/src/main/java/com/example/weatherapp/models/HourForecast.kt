package com.example.weatherapp.models

data class HourForecast(
    val time: String,
    val temp_c: Double,
    val temp_f: Double,
    val is_day: Int,
    val condition: Condition,
    val wind_mph: Double,
    val wind_kph: Double,
    val wind_dir: String,
    val preassure_mb: Double,
    val humidity: Int,
    val cloud: Int,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val chance_of_rain: Int,
    val chance_of_snow: Int
)