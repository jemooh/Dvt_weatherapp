package com.tech.dvtweatherapp.data.local.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tech.dvtweatherapp.data.local.dao.FavouriteWeatherDao
import com.tech.dvtweatherapp.data.local.dao.ForecastDao
import com.tech.dvtweatherapp.data.local.dao.WeatherDao
import com.tech.dvtweatherapp.data.local.model.FavouriteWeather
import com.tech.dvtweatherapp.data.local.model.Forecast
import com.tech.dvtweatherapp.data.local.model.Weather

@Database(
    entities = [Weather::class, Forecast::class, FavouriteWeather::class],
    version = 2

)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase() {
    abstract val weatherDao: WeatherDao
    abstract val forecastDao: ForecastDao
    abstract val favouriteWeatherDao: FavouriteWeatherDao

    companion object {
        const val DATABASE_NAME = "weather_db"
    }
}
