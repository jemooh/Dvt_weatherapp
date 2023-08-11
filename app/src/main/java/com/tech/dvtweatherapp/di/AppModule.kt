package com.tech.dvtweatherapp.di


import android.content.Context
import androidx.room.Room
import com.tech.dvtweatherapp.data.local.datasource.SharedPreferences
import com.tech.dvtweatherapp.data.local.datasource.WeatherDatabase
import com.tech.dvtweatherapp.data.remote.api.WeatherApiService
import com.tech.dvtweatherapp.data.repository.WeatherRepository
import com.tech.dvtweatherapp.data.repository.WeatherRepositoryImpl
import com.tech.dvtweatherapp.ui.viewmodel.FavouriteWeatherViewModel
import com.tech.dvtweatherapp.ui.viewmodel.WeatherViewModel
import com.tech.dvtweatherapp.utils.Constants
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

const val baseUrl: String = Constants.BASE_URL
const val apiKey: String = Constants.APIKEY

val appModule = module {
    single { createNetworkClient(baseUrl, apiKey) }
    single { (get() as? Retrofit)?.create(WeatherApiService::class.java) }

    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java,
            WeatherDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single {
        get<WeatherDatabase>().weatherDao
    }

    single {
        get<WeatherDatabase>().forecastDao
    }

    single {
        get<WeatherDatabase>().favouriteWeatherDao
    }

    factory<WeatherRepository> {
        WeatherRepositoryImpl(
            weatherApiService = get(),
            weatherDao = get(),
            forecastDao = get(),
            favouriteWeatherDao = get()
        )
    }

    viewModel {
        WeatherViewModel(weatherRepository = get())
    }

    viewModel {
        FavouriteWeatherViewModel(weatherRepository = get())
    }
    single {
        androidApplication().getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
    }
    single {
        SharedPreferences(get())
    }
}
