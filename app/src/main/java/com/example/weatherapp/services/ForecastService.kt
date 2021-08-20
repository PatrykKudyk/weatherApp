package com.example.weatherapp.services

import com.example.weatherapp.models.CityForecast
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.Query

interface ForecastService {


    @Headers(
        "x-rapidapi-key: e939a2afd1mshe145d489614c48bp17a6f6jsn897ad2d2033b",
        "x-rapidapi-host: weatherapi-com.p.rapidapi.com"
    )
    @GET("forecast.json")
    fun getForecast(@Query("q" ) cityName: String, @Query("days") days: Int, @Query("lang") language: String): Call<CityForecast>
}