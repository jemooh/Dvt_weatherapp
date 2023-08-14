package com.tech.dvtweatherapp.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.tech.dvtweatherapp.data.local.model.FavouriteWeather
import com.tech.dvtweatherapp.data.local.model.Forecast
import com.tech.dvtweatherapp.ui.viewmodel.FavouriteWeatherViewModel
import com.tech.dvtweatherapp.ui.viewmodel.WeatherViewModel
import com.tech.dvtweatherapp.utils.Constants.DEGREE_SYMBOL
import com.tech.dvtweatherapp.utils.Util.Companion.getCurrentDayOfTheWeek
import com.tech.dvtweatherapp.utils.Util.Companion.getDateLatestUpdated
import com.tech.dvtweatherapp.utils.Util.Companion.getFavouriteDrawable
import com.tech.dvtweatherapp.utils.Util.Companion.getWeatherBackgroundColor
import com.tech.dvtweatherapp.utils.Util.Companion.getWeatherBackgroundDrawable
import com.tech.dvtweatherapp.utils.Util.Companion.getWeatherIconDrawable
import java.util.*
import org.koin.androidx.compose.getViewModel

@Preview
@Composable
fun CurrentLocationWeather() {
    val context = LocalContext.current
    val weatherViewModel = getViewModel<WeatherViewModel>()
    weatherViewModel.getCurrentWeather()
    weatherViewModel.getForecast()
    val uiState = weatherViewModel.state.collectAsState().value

    val favouriteWeatherViewModel = getViewModel<FavouriteWeatherViewModel>()
    val weather = uiState.weather
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color =
                getWeatherBackgroundColor(
                    weather?.mainDescription
                )
            )
    ) {
        Box(
            Modifier
                .height(350.dp)
                .paint(
                    painter = rememberDrawablePainter(
                        drawable = getWeatherBackgroundDrawable(
                            context = context,
                            weather?.mainDescription
                        )
                    ),
                    contentScale = ContentScale.FillBounds
                )
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 32.dp)
                    .padding(end = 32.dp)
                    .clickable {
                        weather?.apply {
                            val favouriteWeather = FavouriteWeather(
                                id,
                                UUID
                                    .randomUUID()
                                    .toString(),
                                dt,
                                locationName = locationName,
                                minTemp = minTemp,
                                maxTemp = maxTemp,
                                temp = temp,
                                lat,
                                lon,
                                icon,
                                description,
                                mainDescription,
                                true
                            )
                            favouriteWeatherViewModel.saveFavouriteWeather(favouriteWeather)
                        }
                    },
                painter = rememberDrawablePainter(
                    getFavouriteDrawable(
                        context,
                        uiState.weather?.isFavourite
                    )
                ),
                contentDescription = null,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .wrapContentWidth()
                    .padding(16.dp)
                    .wrapContentHeight()

            ) {
                Text(
                    text = uiState.weather?.locationName.toString(),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = uiState.weather?.temp.toString() + DEGREE_SYMBOL,
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = uiState.weather?.mainDescription.toString()
                        .uppercase(Locale.getDefault()),
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)

            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp)
                        .padding(start = 16.dp)
                        .padding(end = 16.dp)
                ) {
                    Temp(uiState.weather?.minTemp.toString(), "min")
                    Temp(uiState.weather?.temp.toString(), "Current")
                    Temp(uiState.weather?.maxTemp.toString(), "max")
                }
                Spacer(Modifier.size(16.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .height(1.dp),
                    text = ""
                )
                Spacer(Modifier.size(32.dp))
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(
                        items = uiState.forecast,
                        itemContent = {
                            if (it.day != getCurrentDayOfTheWeek()) {
                                ForecastListItems(forecast = it)
                            }
                        }
                    )
                }
            }
        }

        if (uiState.weather?.dt != null) {
            Text(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .padding(end = 16.dp)
                    .padding(start = 16.dp),
                text = String.format(
                    "Last updated %s",
                    getDateLatestUpdated(uiState.weather.dt)
                ),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Temp(temp: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = temp + DEGREE_SYMBOL,
        )
        Text(
            text = label,
        )
    }
}

@Composable
fun ForecastListItems(forecast: Forecast) {
    val context = LocalContext.current
    Box(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(end = 16.dp)
            .padding(start = 16.dp)
            .padding(top = 8.dp)
    ) {
        Text(forecast.day, modifier = Modifier.align(Alignment.CenterStart))
        Image(
            modifier = Modifier.align(Alignment.Center),
            painter = rememberDrawablePainter(
                drawable = getWeatherIconDrawable(
                    context = context,
                    forecast.main
                )
            ),
            contentDescription = null,
        )
        Text(
            forecast.temp.toString() + DEGREE_SYMBOL,
            modifier = Modifier.align(Alignment.CenterEnd)
        )
    }
}
