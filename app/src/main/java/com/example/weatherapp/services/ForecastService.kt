package com.example.weatherapp.services

import com.example.weatherapp.models.api.CityForecast
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface ForecastService {


    @Headers( value = [
        "x-rapidapi-key: e939a2afd1mshe145d489614c48bp17a6f6jsn897ad2d2033b",
        "x-rapidapi-host: weatherapi-com.p.rapidapi.com",
        "Content-Type: application/json",
        "Accept: application/json"
    ])
    @GET("forecast.json")
    fun getForecast(@QueryMap params: Map<String, String>): Call<CityForecast>
}