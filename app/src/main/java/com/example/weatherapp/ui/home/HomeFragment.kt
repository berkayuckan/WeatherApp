package com.example.weatherapp.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.model.WeatherResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        if (checkLocationPermission()){
            requestLocationonUpdates()
        }else{
            requestLocationPermission()
        }

        viewModel.getDataFromService()
        initObserve()

        binding.button.setOnClickListener {
            requestLocationonUpdates()
        }
        return binding.root
    }



    private fun initObserve(){
        viewModel.weatherData.observe(viewLifecycleOwner){weatherResponse ->
            weatherResponse?.let {
                initRecyclerView(it)
            }
        }
    }


    private fun initRecyclerView(weatherResponse: WeatherResponse?){
        val adapter = weatherResponse?.let {HomeAdapter(it){ position ->
            val time = weatherResponse.daily.time[position]
            val maxTemp = weatherResponse.daily.apparentTemperatureMax[position]
            val minTemp = weatherResponse.daily.apparentTemperatureMin[position]
            Toast.makeText(requireContext(), position.toString(), Toast.LENGTH_SHORT).show()
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(time,maxTemp.toFloat(),minTemp.toFloat()))
        }}
        binding.recyclerView.adapter = adapter
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    // This method checks if the user has granted location permission.
    private fun checkLocationPermission(): Boolean {
        // Get the current permission status.
        val permissionResult = ContextCompat.checkSelfPermission(
            requireActivity(),
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        // Return true if the user has granted location permission.
        return permissionResult == PackageManager.PERMISSION_GRANTED
    }

    // This method requests location permission from the user.
    private fun requestLocationPermission(){
        // Request location permission from the user.
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_LOCATION_PERMISSION
        )
    }

    private fun requestLocationonUpdates(){
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED){
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null){
                val latitude = location.latitude
                val longitude = location.longitude
                Log.d("enlemmm","$latitude")
                Log.d("enlemmm","$longitude")

                viewModel.enlemVeBoylam(latitude, longitude)
                initObserve()

            }
        }
    }

    companion object{
        private const val REQUEST_LOCATION_PERMISSION = 1
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                requestLocationonUpdates()
            }else{
                // Handle the case when location permission is not granted
            }
        }
    }

}