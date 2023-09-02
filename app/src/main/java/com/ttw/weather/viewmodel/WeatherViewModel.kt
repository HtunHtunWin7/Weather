package com.ttw.weather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ttw.weather.network.response.AstronResponse
import com.ttw.weather.network.response.SearchResponse
import com.ttw.weather.repository.WeatherRepository
import com.ttw.weather.view.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val repository: WeatherRepository) :
    ViewModel() {

    private val _weather by lazy {
        MutableLiveData<ViewState<SearchResponse>>()
    }

    private val _astronomy by lazy {
        MutableLiveData<ViewState<AstronResponse>>()
    }

    val weather: LiveData<ViewState<SearchResponse>>
        get() = _weather

    val astronomy : LiveData<ViewState<AstronResponse>>
        get() = _astronomy

    fun getWeather(name: String) = viewModelScope.launch {
        _weather.postValue(ViewState.Loading())
        repository.getWeather(name).let { response ->
            if (response.isSuccessful) {
                val res = response.body()
                _weather.postValue(ViewState.Success(res!!))
            } else {
                _weather.postValue(ViewState.Error(response.message()))
            }
        }
    }

    fun getAstronomy(name: String, date: String) = viewModelScope.launch {
        _astronomy.postValue(ViewState.Loading())
        repository.getAstronomy(name,date).let {
            response ->
            if (response.isSuccessful){
                val res = response.body()
                _astronomy.postValue(ViewState.Success(res!!))
            }else{
                _astronomy.postValue(ViewState.Error(response.message()))
            }
        }
    }

}