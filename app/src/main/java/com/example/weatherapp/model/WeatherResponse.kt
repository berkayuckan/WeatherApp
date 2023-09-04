package com.example.weatherapp.model


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_response")
data class WeatherResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,

    @Embedded
    val currentWeather: CurrentWeather,

    @Embedded
    val daily: Daily,

    var icons : List<Int>

)