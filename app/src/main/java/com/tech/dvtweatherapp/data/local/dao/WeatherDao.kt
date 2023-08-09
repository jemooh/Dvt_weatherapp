package com.tech.dvtweatherapp.data.local.dao

import androidx.room.*
import com.tech.dvtweatherapp.data.local.model.Weather
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao : CoroutineBaseDao<Weather> {
    @Query("SELECT * FROM Weather ORDER BY dt DESC LIMIT 1 ")
    fun getCurrentWeather(): Flow<Weather>

    @Query("SELECT * FROM Weather Where isFavourite=1 ")
    fun getFavouriteWeather(): Flow<List<Weather>>
}
