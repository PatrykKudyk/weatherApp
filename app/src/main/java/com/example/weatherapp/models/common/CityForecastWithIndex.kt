package com.example.weatherapp.models.common

import com.example.weatherapp.models.api.CityForecast

data class CityForecastWithIndex(
    val index: Int,
    val forecast: CityForecast
)
