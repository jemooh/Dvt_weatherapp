package com.tech.dvtweatherapp.data.remote.model

import com.google.gson.annotations.SerializedName
import com.tech.dvtweatherapp.data.remote.model.Coord
import com.tech.dvtweatherapp.data.remote.model.Main
import com.tech.dvtweatherapp.data.remote.model.WeatherItem

data class WeatherResponse(

    @field:SerializedName("coord")
    val coord: Coord? = null,

    @field:SerializedName("main")
    val main: Main? = null,

    @field:SerializedName("weather")
    val weather: List<WeatherItem?>? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("dt")
    val dt: Int? = null
)
