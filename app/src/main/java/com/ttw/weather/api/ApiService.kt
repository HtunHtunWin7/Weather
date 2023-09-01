package com.ttw.weather.api

import com.ttw.weather.model.Weather
import com.ttw.weather.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

   /* @Headers(
        "Content-Type: application/json",
        "Authorization : Token ${Constants.API_KEY}"
    )*/
    @GET("current.json?key=682852b995364b24aad171628233108 &q=London&aqi=no")
    suspend fun getWeather(): Response<Weather>

}