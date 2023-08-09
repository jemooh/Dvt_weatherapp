package com.tech.dvtweatherapp.data.local.dao

import androidx.room.*
import com.tech.dvtweatherapp.data.local.model.Forecast
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao : CoroutineBaseDao<Forecast> {
    @Query("SELECT * FROM Forecast a order by dt ASC Limit 6 ")
    fun getForecast(): Flow<List<Forecast>>
}
