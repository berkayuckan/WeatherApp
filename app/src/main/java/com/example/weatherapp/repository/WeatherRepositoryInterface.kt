package com.example.weatherapp.repository

import com.example.weatherapp.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepositoryInterface {

    suspend fun getDataFromService(latitude: Double, longitude: Double): Flow<WeatherResponse?>
}