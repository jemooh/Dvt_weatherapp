package com.tech.dvtweatherapp.data.repository

import com.tech.dvtweatherapp.data.local.dao.FavouriteWeatherDao
import com.tech.dvtweatherapp.data.local.dao.ForecastDao
import com.tech.dvtweatherapp.data.local.dao.WeatherDao
import com.tech.dvtweatherapp.data.local.model.FavouriteWeather
import com.tech.dvtweatherapp.data.local.model.Forecast
import com.tech.dvtweatherapp.data.local.model.Weather
import com.tech.dvtweatherapp.data.remote.api.WeatherApiService
import com.tech.dvtweatherapp.data.remote.model.Result
import com.tech.dvtweatherapp.utils.Constants
import com.tech.dvtweatherapp.utils.Util
import java.io.IOException
import java.util.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber

internal class WeatherRepositoryImpl(
    private val weatherApiService: WeatherApiService,
    private val weatherDao: WeatherDao,
    private val forecastDao: ForecastDao,
    private val favouriteWeatherDao: FavouriteWeatherDao,
    private val isDispatcher: CoroutineDispatcher = Dispatchers.IO
) : WeatherRepository {
    override suspend fun fetchCurrentLocationWeather(lat: String, lon: String): Result<Boolean> {
        return withContext(isDispatcher) {
            try {
                Result.Loading
                val units = Constants.UNITS_METRIC
                val result = weatherApiService.getCurrentWeather(lat, lon, units)
                if (result.isSuccessful) {
                    result.body()?.apply {
                        val weather = Weather(
                            id,
                            dt,
                            name,
                            main?.tempMin?.toInt(),
                            main?.tempMax?.toInt(),
                            main?.temp?.toInt(),
                            coord?.lat,
                            coord?.lon,
                            weather?.get(0)?.icon,
                            weather?.get(0)?.description,
                            weather?.get(0)?.main,
                            Date()
                        )
                        weatherDao.insertAsync(weather)
                    }
                    Result.Success(true)
                } else {
                    Result.Success(false)
                    Result.Error(Exception(result.errorBody().toString()))
                }
            } catch (e: IOException) {
                Result.Error(Exception("Error Occurred"))
                e.printStackTrace()
                Timber.e(e)
            }
            Result.Success(false)
        }
    }

    override suspend fun fetch5dayWeatherForecast(lat: String, lon: String): Result<Boolean> {
        return withContext(isDispatcher) {
            try {
                Result.Loading
                val units = Constants.UNITS_METRIC
                val result = weatherApiService.fetch5dayWeatherForecast(lat, lon, units)
                if (result.isSuccessful) {
                    result.body()?.list?.forEach { listItem ->
                        val forecast = Forecast(
                            Util.getWeekDayFromUTC(listItem.dt),
                            listItem.dt,
                            listItem.main?.temp?.toInt(),
                            listItem.weather?.get(0)?.main
                        )
                        forecastDao.insertAsync(forecast)
                    }

                    Result.Success(true)
                } else {
                    Result.Success(false)
                    Result.Error(Exception(result.errorBody().toString()))
                }
            } catch (e: IOException) {
                Result.Error(Exception("Error Occurred"))
                e.printStackTrace()
                Timber.e(e)
            }
            Result.Success(false)
        }
    }

    override fun getCurrentWeather(): Flow<Weather> {
        return weatherDao.getCurrentWeather()
    }

    override fun getForecast(): Flow<List<Forecast>> {
        return forecastDao.getForecast()
    }

    override fun getFavouriteWeather(): Flow<List<FavouriteWeather>> {
        return favouriteWeatherDao.getFavouriteWeather()
    }

    override suspend fun saveFavouriteCurrentWeather(weather: FavouriteWeather) {
        favouriteWeatherDao.insertAsync(weather)
    }
}
