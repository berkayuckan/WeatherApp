package com.example.weatherapp.repository

import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.network.WeatherService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao
) : WeatherRepositoryInterface{

    override suspend fun getDataFromService(
        latitude: Double,
        longitude: Double
    ) = flow {
        try {
            val weatherResponse = weatherService.getWeatherResult(latitude = latitude, longitude = longitude)
            weatherDao.deleteAll()
            weatherDao.insert(weatherResponse)
            emit(weatherResponse)
        }catch (e: Exception){
            emit(weatherDao.getAll())
        }
    }
}