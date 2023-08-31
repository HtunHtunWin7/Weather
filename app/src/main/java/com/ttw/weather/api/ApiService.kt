package com.ttw.weather.api

import com.ttw.weather.model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather/Luanda")
    suspend fun getWeather(): Response<Weather>

}