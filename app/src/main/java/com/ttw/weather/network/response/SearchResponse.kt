package com.ttw.weather.network.response

import com.ttw.weather.model.Current
import com.ttw.weather.model.Location


data class SearchResponse(
   val location: Location,
   val current: Current
)