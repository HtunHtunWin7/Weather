package com.ttw.weather.network.api

import com.ttw.weather.network.response.AstronResponse
import com.ttw.weather.network.response.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

   /* @Headers(
        "Content-Type: application/json",
        "Authorization : Token ${Constants.API_KEY}"
    )*/
    @GET("current.json?key=682852b995364b24aad171628233108")
    suspend fun getWeather(@Query("q")q: String): Response<SearchResponse>

    @GET("astronomy.json?key=682852b995364b24aad171628233108")
    suspend fun getAstronomy(@Query("q")q: String, @Query("dt")dt: String): Response<AstronResponse>

}