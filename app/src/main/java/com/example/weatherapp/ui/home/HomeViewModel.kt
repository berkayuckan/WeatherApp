package com.example.weatherapp.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.repository.WeatherRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val weatherRepository: WeatherRepositoryInterface) : ViewModel() {

    val weatherData: MutableLiveData<WeatherResponse?> = MutableLiveData()

    val latitude = MutableLiveData<Double?>(0.0)
    val longitude = MutableLiveData<Double?>(0.0)

    fun enlemVeBoylam(e : Double, b : Double){
        latitude.postValue(e)
        longitude.postValue(b)
        getDataFromService()
    }

    fun getDataFromService() = viewModelScope.launch {
        latitude.value?.let {
            longitude.value?.let { it1 ->
                weatherRepository.getDataFromService(latitude = it, longitude = it1).collect{data ->
                    weatherData.postValue(data)
                }
            }
        }
    }

}