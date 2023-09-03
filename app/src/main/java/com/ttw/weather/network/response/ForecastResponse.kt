package com.ttw.weather.network.response

import com.ttw.weather.model.Condition
import com.ttw.weather.model.Forecastday

data class ForecastResponse(
    val forecast: Forecastday
)