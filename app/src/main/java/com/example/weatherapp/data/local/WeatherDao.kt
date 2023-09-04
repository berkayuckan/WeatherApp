package com.example.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.WeatherResponse

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather_response")
    suspend fun getAll(): WeatherResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherResponse: WeatherResponse)

    @Query("DELETE FROM weather_response")
    suspend fun deleteAll()
}