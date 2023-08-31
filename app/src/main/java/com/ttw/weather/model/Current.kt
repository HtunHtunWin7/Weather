package com.ttw.weather.model


data class Current(
    val last_updated_epoch: String?,
    val last_updated: String?,
    val temp_c: String?,
    val temp_f: String?,
    val is_day: String?,
    val condition : Condition,
    val wind_mph : Condition,
    val wind_kph : Condition,
    val wind_degree : Condition

)

data class Condition(
    val text: String?,
    val icon: String?,
    val code: String?,
)