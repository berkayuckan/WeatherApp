package com.example.weatherapp.network

import com.example.weatherapp.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("v1/forecast")
    suspend fun getWeatherResult(
        @Query("latitude") latitude: Double = 38.4127,
        @Query("longitude") longitude: Double = 27.1384,
        @Query("current_weather") currentWeather: Boolean = true,
        @Query("daily") daily: String = "weathercode,apparent_temperature_max,apparent_temperature_min",
        @Query("timezone") timezone: String = "auto",
        @Query("temperature_unit") temperatureUnit: String = "celsius",
        @Query("forecast_days") forecastDays: Int = 14
    ): WeatherResponse
}