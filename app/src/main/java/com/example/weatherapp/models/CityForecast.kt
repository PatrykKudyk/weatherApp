package com.example.weatherapp.models

data class CityForecast(
    val location: Location,
    val current: CurrentWeather,
    val forecast: Forecast
)