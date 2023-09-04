package com.example.weatherapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.weatherapp.R
import com.example.weatherapp.databinding.CurrentlyItemBinding
import com.example.weatherapp.databinding.NextDaysItemBinding
import com.example.weatherapp.model.WeatherResponse
import com.example.weatherapp.util.Constants

class HomeAdapter(weatherResponse: WeatherResponse, val onClick: (Int) -> Unit): RecyclerView.Adapter<HomeAdapter.WeatherViewHolder>(){

    private val currentWeather = weatherResponse.currentWeather
    private val dates = weatherResponse.daily.time
    private val minTem = weatherResponse.daily.apparentTemperatureMin
    private val maxTem = weatherResponse.daily.apparentTemperatureMax
    private val icons = weatherResponse.icons

    inner class WeatherViewHolder(itemView : View): ViewHolder(itemView){
        fun bind(
            time : String,
            minTemp : Double,
            maxTemp : Double,
            icon: Int
        ){
            if (adapterPosition == Constants.CURRENT_DAY){
                val binding = CurrentlyItemBinding.bind(itemView)
                binding.apply {
                    tvTemperature.text = currentWeather.temperature.toString().plus("C")
                    tvWindSpeed.text = currentWeather.windspeed.toString().plus("Km/h")
                    tvWindDirection.text = currentWeather.winddirection.toString()
                    ivWeatherIcon.setImageResource(icon)
                }
            }
            else{
                val binding = NextDaysItemBinding.bind(itemView)
                binding.apply {
                    tvDate.text = time
                    tvMinTemp.text = minTemp.toString()
                    tvMaxTemp.text = maxTemp.toString()
                    ivWeatherIcon.setImageResource(icon)
                    cardViewNextDay.setOnClickListener {
                        onClick(adapterPosition)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view : View = when(viewType){
            Constants.CURRENT_DAY -> layoutInflater.inflate(
                R.layout.currently_item,
                parent,
                false
            )
            Constants.NEXT_DAYS -> layoutInflater.inflate(
                R.layout.next_days_item,
                parent,
                false
            )
            else -> throw IllegalArgumentException()
        }
        return WeatherViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dates.size
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.bind(
            time = dates[position],
            maxTemp = maxTem[position],
            minTemp = minTem[position],
            icon = icons[position]
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0){
            Constants.CURRENT_DAY
        }else
            Constants.NEXT_DAYS
    }

}