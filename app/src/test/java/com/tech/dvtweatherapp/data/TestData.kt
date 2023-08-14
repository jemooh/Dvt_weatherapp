package com.tech.dvtweatherapp.data

import com.tech.dvtweatherapp.data.local.model.FavouriteWeather
import com.tech.dvtweatherapp.data.local.model.Forecast
import com.tech.dvtweatherapp.data.local.model.Weather
import com.tech.dvtweatherapp.ui.viewmodel.FavouriteWeatherState
import com.tech.dvtweatherapp.ui.viewmodel.WeatherForecastState
import java.util.*

internal val testWeatherResponseResult =
    WeatherForecastState(
        isSuccessRefreshingCurrentWeather = true
    )

internal val testForecastResponseResult =
    WeatherForecastState(
        isSuccessRefreshingCurrentWeather = true, isSuccessRefreshingForecast = true
    )

internal val testFetchFavouriteWeatherResult =
    FavouriteWeatherState(
        favouriteWeather = emptyList()
    )

val currentWeather =
    Weather(
        184736,
        1650209951,
        "Nairobi South",
        19,
        30,
        24,
        -1.3188,
        36.8511,
        "09n",
        "overcast clouds",
        "Clouds",
        Date()
    )

val weatherForecastList = (0..5).map {
    Forecast(
        day = "Monday", dt = 234324, temp = 13, main = "clouds"
    )
}

val weatherFavouriteList = (0..5).map {
    FavouriteWeather(
        184736, "3440",
        1650209951,
        "Nairobi South",
        19,
        30,
        24,
        -1.3188,
        36.8511,
        "09n",
        "overcast clouds","Rains",
        true
    )
}
