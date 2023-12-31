package com.tech.dvtweatherapp.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.tech.dvtweatherapp.data.local.model.FavouriteWeather
import com.tech.dvtweatherapp.utils.Constants
import com.tech.dvtweatherapp.utils.Util
import com.tech.dvtweatherapp.utils.Util.Companion.getMinMonthFromUTC
import com.tech.dvtweatherapp.utils.Util.Companion.getMinWeekDayFromUTC
import com.tech.dvtweatherapp.ui.viewmodel.FavouriteWeatherViewModel
import org.koin.androidx.compose.getViewModel

@Preview
@Composable
fun FavouriteLocationWeather() {
    val favouriteWeatherViewModel = getViewModel<FavouriteWeatherViewModel>()
    favouriteWeatherViewModel.getFavouriteWeather()
    val uiState = favouriteWeatherViewModel.state.collectAsState().value
    if (uiState.favouriteWeather.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(
                color = Color.Black,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center,
                text = "Your weather favourite list is Empty"
            )
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(
                items = uiState.favouriteWeather,
                itemContent = {
                    FavouriteListItem(favouriteWeather = it)
                }
            )
        }
    }
}

@Composable
fun FavouriteListItem(favouriteWeather: FavouriteWeather) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable { },
        elevation = 6.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(15.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier =
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                ) {
                    Text(
                        getMinWeekDayFromUTC(favouriteWeather.dt),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                    Text(
                        getMinMonthFromUTC(favouriteWeather.dt),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                }

                Image(
                    modifier = Modifier
                        .padding(8.dp)
                        .width(80.dp)
                        .heightIn(80.dp),
                    painter = rememberDrawablePainter(
                        drawable = Util.getWeatherIconDrawable(
                            context = context,
                            favouriteWeather.mainDescription
                        )
                    ),
                    contentDescription = null,
                )

                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentWidth()
                        .wrapContentHeight()
                ) {

                    Text(
                        text = favouriteWeather.temp.toString() + Constants.DEGREE_SYMBOL,
                        style = MaterialTheme.typography.h4,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        favouriteWeather.locationName.toString(),
                        style = MaterialTheme.typography.h6
                    )
                }
            }
            Text(
                text = favouriteWeather.mainDescription.toString(),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 2.dp)
            )
        }
    }
}
