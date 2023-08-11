package com.tech.dvtweatherapp.ui.theme

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TopBar() {

    TopAppBar(
        title = { Text("Weather App") }
    )
}

@Preview
@Composable
fun TopBarPreview() {
    WeatherAppTheme {
        TopBar()
    }
}
