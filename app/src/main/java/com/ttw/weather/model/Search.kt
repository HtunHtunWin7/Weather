package com.ttw.weather.model

data class Search(
    val id: String,
    val name: String,
    val region: String,
    val country: String,
    val lat: String,
    val lon: String,
    val url: String
)
