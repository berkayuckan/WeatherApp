package com.example.weatherapp.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater)

        Toast.makeText(requireContext(), "${args.time} -- ${args.maxTemp}", Toast.LENGTH_SHORT).show()
        initUI()

        return binding.root
    }

    private fun initUI(){
        with(binding){
            tvDate.text = args.time
            tvMaxtemperature.text = args.maxTemp.toString()
            tvMintemperature.text = args.minTemp.toString()
        }
    }
}