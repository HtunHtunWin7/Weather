package com.ttw.weather.model


data class Forecastday(
    val forecastday: List<DayForecast>
)

data class DayForecast(
    val date: String,
    val day: Day
)

data class Day(
    val maxtemp_c : String,
    val condition: Condition
)
