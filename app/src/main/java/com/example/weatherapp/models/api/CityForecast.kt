package com.example.weatherapp.models.api

data class CityForecast(
    val location: Location,
    val current: CurrentWeather,
    val forecast: Forecast
)