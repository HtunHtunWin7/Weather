package com.ttw.weather.network.response

import com.ttw.weather.model.Astronomy
import com.ttw.weather.model.Location

data class AstronResponse(
    val location: Location,
    val astronomy: List<Astronomy>
)
