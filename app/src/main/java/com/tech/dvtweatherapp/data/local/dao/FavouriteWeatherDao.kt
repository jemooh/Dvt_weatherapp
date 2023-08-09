package com.tech.dvtweatherapp.data.local.dao

import androidx.room.*
import com.tech.dvtweatherapp.data.local.model.FavouriteWeather
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteWeatherDao : CoroutineBaseDao<FavouriteWeather> {
    @Query("SELECT * FROM FavouriteWeather ")
    fun getFavouriteWeather(): Flow<List<FavouriteWeather>>
}
