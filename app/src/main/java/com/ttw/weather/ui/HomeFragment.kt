package com.ttw.weather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ttw.weather.databinding.FragmentHomeBinding
import com.ttw.weather.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date


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

        viewModel.getWeather("Yangon")
        observeData()
        return binding.root
    }

    private fun observeData(){
        viewModel.weather.observe(viewLifecycleOwner, Observer {
            binding.txtTitle.text = it.value?.location?.name.toString()
            binding.txtTempC.text = it.value?.current?.temp_c?.replace(".0","").toString()
            binding.txtCurrentDate.text = it.value?.current?.last_updated.toString()
            binding.txtCondition.text = it.value?.current?.condition?.text.toString()
        })
    }

}