package com.tech.dvtweatherapp.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ktx.awaitMap
import com.tech.dvtweatherapp.R
import com.tech.dvtweatherapp.ui.viewmodel.FavouriteWeatherViewModel
import com.tech.dvtweatherapp.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun FavouriteWeatherMapView() {
    val mapView = rememberMapViewWithLifecycle()
    val favouriteWeatherViewModel = getViewModel<FavouriteWeatherViewModel>()
    favouriteWeatherViewModel.getFavouriteWeather()
    val uiState = favouriteWeatherViewModel.state.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {
        AndroidView({ mapView }) { mapView ->
            CoroutineScope(Dispatchers.Main).launch {
                val map = mapView.awaitMap()
                map.uiSettings.isZoomControlsEnabled = true

                if (uiState.favouriteWeather.isNotEmpty()) {
                    uiState.favouriteWeather.forEach { weather ->
                        val location = LatLng(weather.lat ?: 0.0, weather.lon ?: 0.0)
                        val markerOptions = MarkerOptions()
                            .title(weather.locationName)
                            .snippet(
                                String.format(
                                    "%s with temp:%s" + Constants.DEGREE_SYMBOL,
                                    weather.description,
                                    weather.temp
                                )
                            )
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.weather_pin))
                            .position(location)
                        map.addMarker(markerOptions)
                    }
                    val firstItem = uiState.favouriteWeather[0]
                    val location2 = LatLng(firstItem.lat ?: 0.0, firstItem.lon ?: 0.0)
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(location2, 15.0f))
                }
            }
        }
    }
}
