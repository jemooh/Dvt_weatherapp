package com.tech.dvtweatherapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.tech.dvtweatherapp.utils.Util.Companion.checkIfLocationPermissionIsEnabled
import com.tech.dvtweatherapp.utils.Util.Companion.validateAndForceLocationSetting
import com.tech.dvtweatherapp.data.local.datasource.SharedPreferences
import com.tech.dvtweatherapp.ui.composable.NavigationPage
import com.tech.dvtweatherapp.ui.theme.WeatherAppTheme
import com.tech.dvtweatherapp.ui.viewmodel.WeatherViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val sharedPreferences: SharedPreferences by inject()
    private val weatherViewModel: WeatherViewModel by viewModel()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun MainScreen() {
        WeatherAppTheme {
            Scaffold(){
                NavigationPage()
            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainScreenPreview() {
        MainScreen()
    }

    private fun fetchRemoteWeather() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                val lat = location?.latitude.toString()
                val lon = location?.longitude.toString()
                weatherViewModel.fetchCurrentWeather(lat, lon)
                weatherViewModel.fetch5dayWeatherForecast(lat, lon)
            }
    }

    override fun onStart() {
        super.onStart()
        checkIfLocationPermissionIsEnabled(this, sharedPreferences)
        validateAndForceLocationSetting(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onResume() {
        super.onResume()
        fetchRemoteWeather()
    }
}
