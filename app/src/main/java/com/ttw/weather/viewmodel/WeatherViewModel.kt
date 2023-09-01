package com.ttw.weather.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttw.weather.model.Weather
import com.ttw.weather.repository.WeatherRepository
import com.ttw.weather.view.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    private val _weather by lazy {
        MutableLiveData<ViewState<Weather>>()
    }

    val weather: LiveData<ViewState<Weather>>
        get() = _weather

    fun getWeather() = viewModelScope.launch {
        _weather.postValue(ViewState.Loading())
        repository.getWeather().let { response ->
            if (response.isSuccessful) {
                val res = response.body()
                _weather.postValue(ViewState.Success(res!!))
            } else {
                _weather.postValue(ViewState.Error(response.message()))
            }
        }
    }

}