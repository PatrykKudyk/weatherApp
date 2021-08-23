package com.example.weatherapp.models

data class ForecastDay(
    val date: String,
    val day: DayForecast,
    val astro: Astro,
    val hour: ArrayList<HourForecast>
)