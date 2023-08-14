package com.tech.dvtweatherapp.viewmodel

import com.google.common.truth.Truth
import com.tech.dvtweatherapp.data.weatherFavouriteList
import com.tech.dvtweatherapp.data.local.model.FavouriteWeather
import com.tech.dvtweatherapp.data.repository.WeatherRepository
import com.tech.dvtweatherapp.ui.viewmodel.FavouriteWeatherViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.spekframework.spek2.Spek

@ExperimentalTime
@ExperimentalCoroutinesApi
internal class FavouriteViewModelTest : Spek({

    val weatherRepository = mockk<WeatherRepository>()
    val favouriteWeatherViewModel by lazy { FavouriteWeatherViewModel(weatherRepository = weatherRepository) }

    val dispatcher = TestCoroutineDispatcher()

    beforeGroup {
        Dispatchers.setMain(dispatcher = dispatcher)
    }

    group("Fetching Favourite Weather Information") {


        test("Test Saving Favourite weather to db") {

            runBlocking {
                val favourite = FavouriteWeather(
                    184736, "3440",
                    1650209951,
                    "Nairobi South",
                    19,
                    30,
                    24,
                    -1.3188,
                    36.8511,
                    "09n",
                    "overcast clouds","Clouds",
                    true
                )

                coEvery {
                    favouriteWeatherViewModel.saveFavouriteWeather(favourite)
                } returns Unit

                coEvery { weatherRepository.getFavouriteWeather() } returns flowOf(
                    weatherFavouriteList
                )
                val favouriteWeather = weatherRepository.getFavouriteWeather()
                Truth.assertThat(favouriteWeather).isNotNull()

            }
        }

        test("Test is fetch favourite weather information is fetched successfully") {
            runBlocking {
                coEvery {
                    weatherRepository.getFavouriteWeather()
                } returns flowOf(weatherFavouriteList)
                val result = weatherRepository.getFavouriteWeather().toList()
                Truth.assertThat(result).isNotEmpty()
            }
        }
    }

    afterGroup {
        Dispatchers.resetMain()
    }
})
