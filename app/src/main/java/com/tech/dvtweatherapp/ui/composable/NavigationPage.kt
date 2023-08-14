package com.tech.dvtweatherapp.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tech.dvtweatherapp.R
import com.tech.dvtweatherapp.ui.composable.component.DrawerContent
import com.tech.dvtweatherapp.ui.theme.CloudyBlue200
import com.tech.dvtweatherapp.ui.viewmodel.FavouriteWeatherViewModel
import com.tech.dvtweatherapp.ui.viewmodel.WeatherViewModel
import com.tech.dvtweatherapp.utils.Util
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationPage() {

    val weatherViewModel = getViewModel<WeatherViewModel>()
    weatherViewModel.getCurrentWeather()
    val uiState = weatherViewModel.state.collectAsState().value
    val weather = uiState.weather

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    ModalNavigationDrawer(drawerContent = { DrawerContent(navController,drawerState) }, drawerState = drawerState) {

        Scaffold(topBar = {
            TopAppBar(
                title = {},
                backgroundColor = Util.getWeatherBackgroundColor(
                    weather?.mainDescription
                ),
                elevation = 0.dp,
                navigationIcon = {
                IconButton(onClick = {

                    if(drawerState.isClosed){
                        coroutineScope.launch {
                            drawerState.open()
                        }
                    }else{
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }

                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "Drawer Menu.")
                }

            })
        }) {

            Box(modifier = Modifier
                .padding(it)
            ) {


                NavHost(navController = navController, startDestination = "HomePage") {

                    composable("HomePage") {
                        CurrentLocationWeather()
                    }

                    composable("FavouritePage") {
                        FavouriteLocationWeather()
                    }

                    composable("MapPage") {
                        FavouriteWeatherMapView()
                    }

                }


            }

        }

    }

}