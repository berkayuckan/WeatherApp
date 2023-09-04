package com.example.weatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.model.CurrentWeather
import com.example.weatherapp.model.Daily
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.util.DatabaseConverter

@Database(
    entities = [WeatherResponse::class, CurrentWeather::class, Daily::class],
    version = 3,
    exportSchema = false
)
@TypeConverters(DatabaseConverter::class)
abstract class WeatherDB: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object{
        @Volatile
        private var instance: WeatherDB? = null

        fun getInstance(context: Context): WeatherDB{
            return instance ?: synchronized(this){
                val database = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherDB::class.java,
                    "weather_response_database"
                ).build()
                instance = database
                database
            }
        }
    }
}