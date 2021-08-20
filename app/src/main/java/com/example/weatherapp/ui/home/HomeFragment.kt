package com.example.weatherapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapters.DaysRecyclerViewAdapter
import com.example.weatherapp.adapters.HoursRecyclerViewAdapter
import com.example.weatherapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var hourWeatherAdapter: HoursRecyclerViewAdapter
    private lateinit var dayWeatherAdapter: DaysRecyclerViewAdapter
    private var cityName = ""

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.primaryCity.observe(requireActivity(), Observer{ city ->
            city?.let{
                cityName = city.name
                checkPrimaryCity()
            }
        })
        checkPrimaryCity()
        initListeners()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPrimaryCity() {
        if (cityName.isEmpty()) {
            binding.addCityGroup.visibility = View.VISIBLE
            binding.showWeatherGroup.visibility = View.GONE
        } else {
            binding.textViewCity.text = cityName
            viewModel.fetchForecastData(cityName)
            binding.showWeatherGroup.visibility = View.VISIBLE
            binding.addCityGroup.visibility = View.GONE
        }
    }

    private fun initListeners() {
        val fab = binding.fabSwap
        val addButton = binding.imageAddCity

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_add_city)
        }

        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_add_city)
        }
    }

    private fun initViews() {
        initRecyclerViews()
    }

    private fun initRecyclerViews() {
        hourWeatherAdapter = HoursRecyclerViewAdapter(requireContext())
        binding.recyclerHours.adapter = hourWeatherAdapter
        binding.recyclerHours.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        hourWeatherAdapter.setItems(viewModel.allHourWeather)

        dayWeatherAdapter = DaysRecyclerViewAdapter(requireContext())
        binding.recyclerDays.adapter = dayWeatherAdapter
        binding.recyclerDays.layoutManager = LinearLayoutManager(requireContext())
//        dayWeatherAdapter.setItems(viewModel.allDayWeather)
    }
}