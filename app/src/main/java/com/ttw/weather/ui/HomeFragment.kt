package com.ttw.weather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ttw.weather.R
import com.ttw.weather.databinding.FragmentHomeBinding
import com.ttw.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container,false)

        viewModel.getWeather()
        observeData()
        return binding.root
    }

    private fun observeData(){
        viewModel.weather.observe(viewLifecycleOwner, Observer {
            binding.txtSample.text = it.value?.current?.last_updated.toString()
        })
    }
}