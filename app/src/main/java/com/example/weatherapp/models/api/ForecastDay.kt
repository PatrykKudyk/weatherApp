package com.example.weatherapp.models.api

data class ForecastDay(
    val date: String,
    val day: DayForecast,
    val astro: Astro,
    val hour: ArrayList<HourForecast>
)