package com.ttw.weather.ui

import android.R
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView.OnEditorActionListener
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ttw.weather.adapter.WeatherAdapter
import com.ttw.weather.databinding.FragmentHomeBinding
import com.ttw.weather.utils.Constants
import com.ttw.weather.utils.getDate
import com.ttw.weather.utils.getSharePreference
import com.ttw.weather.utils.showAlertDialog
import com.ttw.weather.view.ViewState
import com.ttw.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private var chooseLocation = "Yangon"
    private lateinit var mAadapter: WeatherAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        loadingState()
        val date = getDate()
        val currentLocation = getSharePreference(requireContext(), Constants.CURRENT_LOCATION)

        mAadapter = WeatherAdapter()

        linearLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.recycler.apply {
            layoutManager = linearLayoutManager
            adapter = mAadapter
        }

        loadWeather("Yangon", date)

        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                chooseLocation = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        binding.edtSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.getWeather(chooseLocation)
                viewModel.getAstronomy(chooseLocation, date)
                observeWeatherData()
                observeAstronomyData()
            }
            false
        })

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().previousBackStackEntry?.let { backEntry ->
                findNavController().popBackStack(
                    backEntry.destination.id,
                    true
                )
            }
        }


        return binding.root
    }

    private fun loadWeather(name: String, date: String) {
        viewModel.getWeather(name)
        viewModel.getAstronomy(name, date)
        viewModel.getForecast(name)
        observeWeatherData()
        observeAstronomyData()
        observeForecastData()
    }

    private fun observeWeatherData() {
        viewModel.weather.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Success -> {
                    val data = it.value?.current
                    binding.txtTitle.text = it.value?.location?.name.toString()
                    binding.txtTempC.text = it.value?.current?.temp_c?.replace(".0", "").toString()
                    binding.txtTempUnit.text = "°C"
                    binding.txtTitleWind.text = "Wind"
                    binding.txtTitleCloud.text = "Cloud"
                    binding.txtTitleHumidity.text = "Humidity"
                    binding.txtWind.text = data?.wind_mph + " mph"
                    binding.txtHumidity.text = data?.humidity + " %"
                    binding.txtCloud.text = data?.cloud + " %"
                }

                is ViewState.Error -> {
                    finishLoading()
                    showAlertDialog(requireContext(), "Something Wrong", it.message.toString())

                    Log.d("Error ##### ", it.message.toString())
                }

                is ViewState.Loading -> {
                    loadingState()
                }
            }

        })
    }

    private fun observeAstronomyData() {
        viewModel.astronomy.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Success -> {
                    val data = it.value?.astronomy?.astro
                    binding.txtSunriseData.text = data?.sunrise
                    binding.txtSunsetData.text = data?.sunset
                    binding.txtMoonriseData.text = data?.moonrise
                    binding.txtMoonsetData.text = data?.moonset
                    binding.txtIlluminationData.text = data?.moon_illumination+ " %"
                    finishLoading()
                }

                is ViewState.Error -> {
                    finishLoading()
                    showAlertDialog(requireContext(), "Something Wrong", it.message.toString())
                }

                is ViewState.Loading -> {
                }

            }
        })
    }

    private fun observeForecastData() {
        viewModel.forecast.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Success -> {
                    val data = it.value?.forecast?.forecastday
                    data?.let { it1 -> mAadapter.addWeather(it1) }
                    Log.d("######", it.value?.forecast?.forecastday?.size.toString())
                }

                is ViewState.Error -> {
                    finishLoading()
                    showAlertDialog(requireContext(), "Something Wrong", it.message.toString())
                }

                is ViewState.Loading -> {}
            }
        })
    }

    private fun loadingState() {
        binding.progressBar.visibility = View.VISIBLE
        binding.scrollView.visibility = View.GONE
    }

    private fun finishLoading() {
        binding.progressBar.visibility = View.GONE
        binding.scrollView.visibility = View.VISIBLE
    }

}