package com.example.weatherapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class CurrentWeather(
    @PrimaryKey
    val id2: Int = 1,

    @SerializedName("is_day")
    val isDay: Int,

    @SerializedName("temperature")
    val temperature: Double,

    @SerializedName("winddirection")
    val winddirection: Int,

    @SerializedName("windspeed")
    val windspeed: Double
)