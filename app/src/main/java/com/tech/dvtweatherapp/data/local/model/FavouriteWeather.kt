package com.tech.dvtweatherapp.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*

@Entity(indices = [Index(value = ["weatherId", "temp", "description"], unique = true)])
data class FavouriteWeather(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val weatherId: String,
    val dt: Int?,
    val locationName: String?,
    val minTemp: Int?,
    val maxTemp: Int?,
    val temp: Int?,
    val lat: Double?,
    val lon: Double?,
    val icon: String?,
    val description: String?,
    val mainDescription: String?,
    var isFavouriteWeather: Boolean = false
)
