package com.example.weatherapp.di

import com.example.weatherapp.data.local.WeatherDao
import com.example.weatherapp.network.WeatherService
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.repository.WeatherRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherService: WeatherService,
        weatherDao: WeatherDao
    ): WeatherRepositoryInterface = WeatherRepository(weatherService, weatherDao)
}