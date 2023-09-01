package com.ttw.weather.repository

import com.ttw.weather.api.ApiService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val service: ApiService)  {
    suspend fun getWeather() = service.getWeather()

}