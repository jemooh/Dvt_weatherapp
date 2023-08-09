package com.tech.dvtweatherapp.data.repository

import com.tech.dvtweatherapp.data.local.model.FavouriteWeather
import com.tech.dvtweatherapp.data.local.model.Forecast
import com.tech.dvtweatherapp.data.local.model.Weather
import com.tech.dvtweatherapp.data.remote.model.Result
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun fetchCurrentLocationWeather(lat: String, lon: String): Result<Boolean>
    suspend fun fetch5dayWeatherForecast(lat: String, lon: String): Result<Boolean>
    fun getCurrentWeather(): Flow<Weather>
    fun getForecast(): Flow<List<Forecast>>
    fun getFavouriteWeather(): Flow<List<FavouriteWeather>>
    suspend fun saveFavouriteCurrentWeather(weather: FavouriteWeather)
}
