package com.example.weatherapp.ui.chosen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.FragmentChosenBinding

class ChosenFragment: Fragment() {

    private lateinit var viewModel: ChosenViewModel
    private var _binding: FragmentChosenBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(ChosenViewModel::class.java)

        _binding = FragmentChosenBinding.inflate(inflater, container, false)

        viewModel.initRecyclerView(requireActivity(), binding)
        bindCitiesList()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindCitiesList() {
        viewModel.savedCities.observe(requireActivity(), Observer { city ->
            city?.let {
                for (city in viewModel.savedCities.value!!){
                    viewModel.fetchCityData(city!!, requireActivity())
                }
            }
        })
    }
}