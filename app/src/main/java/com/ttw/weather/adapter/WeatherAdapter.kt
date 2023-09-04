package com.ttw.weather.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ttw.weather.R
import com.ttw.weather.model.DayForecast
import com.ttw.weather.model.Forecastday
import java.time.LocalDate
import java.util.ArrayList

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private var mList = ArrayList<DayForecast>()

    @SuppressLint("NotifyDataSetChanged")
    fun addWeather(list: List<DayForecast>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = mList[position]
        val temperature = data.day.maxtemp_c
        val icon = data.day.condition.icon.toString()
        val conditon = data.day.condition.text.toString()

        Glide.with(holder.itemView).load(icon).into(holder.imageView)
        holder.txtCondition.text = conditon
        holder.txtTemperature.text = "$temperature °C"
        holder.txtDate.text = data.date
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgWeather)
        val txtTemperature: TextView = itemView.findViewById(R.id.txtTemperature)
        val txtCondition: TextView = itemView.findViewById(R.id.txtCondition)
        val txtDate : TextView = itemView.findViewById(R.id.txtDate)
    }


}
