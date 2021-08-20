package com.example.weatherapp.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServiceBuilder {

    private val URL = "https://weatherapi-com.p.rapidapi.com/"

    private val builder = Retrofit.Builder().baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
    private val retrofit = builder.build()

    fun <S> buildService(serviceType: Class<S>): S {
        return retrofit.create(serviceType)
    }

}