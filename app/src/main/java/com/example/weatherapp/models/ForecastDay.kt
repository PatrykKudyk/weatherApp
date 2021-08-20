package com.example.weatherapp.models

data class ForecastDay(
    val date: String,
    val day: DayForecast,
    val astro: Astro,
    val hourForecast: List<HourForecast>
)