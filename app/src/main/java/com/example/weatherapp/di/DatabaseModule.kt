package com.example.weatherapp.di

import android.content.Context
import com.example.weatherapp.data.local.WeatherDB
import com.example.weatherapp.data.local.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWeaherDB(@ApplicationContext context: Context): WeatherDB{
        return WeatherDB.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDB: WeatherDB): WeatherDao{
        return weatherDB.weatherDao()
    }
}