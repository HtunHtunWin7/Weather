package com.ttw.weather.repository

import com.ttw.weather.network.api.ApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val service: ApiService)  {
    suspend fun getWeather(name: String) = service.getWeather(name)

    suspend fun getAstronomy(name: String, date: String) = service.getAstronomy(name,date)

}