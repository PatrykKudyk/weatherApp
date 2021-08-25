package com.example.weatherapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.transition.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
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

        viewModel.primaryCity.observe(requireActivity(), Observer { city ->
            city?.let {
                cityName = city.name
                checkPrimaryCity()
                makeStartAnimations()
            }
        })
        initListeners()

        return root
    }

    override fun onResume() {
        super.onResume()
        makeStartAnimations()
    }

    override fun onStop() {
        binding.mainConstraint.visibility = View.GONE
        makeViewsGone()
        viewModel.shouldAnimate = true
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkPrimaryCity() {
        if (cityName.isEmpty()) {
            binding.addCityLayout.visibility = View.VISIBLE
            binding.mainConstraint.visibility = View.GONE
            binding.fabSwap.visibility = View.GONE

        } else {

            binding.textViewCity.text = cityName
            viewModel.fetchForecastData(cityName, requireActivity(), binding)

            binding.addCityLayout.visibility = View.GONE
            binding.mainConstraint.visibility = View.VISIBLE
            binding.fabSwap.visibility = View.VISIBLE
        }
    }

    private fun initListeners() {
        binding.fabSwap.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_add_city)
        }

        binding.imageAddCity.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_add_city)
        }

        binding.fabAnimation.setOnClickListener {
            makeStartAnimations()
        }
    }

    private fun makeStartAnimations() {
        if (viewModel.shouldAnimate) {
            binding.mainConstraint.visibility = View.GONE
            makeViewsGone()

            Handler().postDelayed({
                if (_binding != null)
                    animateBackground()
            }, 50)

            Handler().postDelayed({
                if (_binding != null)
                    animateWeather()
            }, 1600)

            viewModel.shouldAnimate = false
        }
    }


    private fun animateBackground() {
        val tBackground = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.transition_weather_background_showup)

        TransitionManager.beginDelayedTransition(binding.mainConstraint, tBackground)

        binding.mainConstraint.visibility = View.VISIBLE

        Handler().postDelayed({
            if (_binding != null) {
                TransitionManager.endTransitions(binding.mainConstraint)
            }
        }, 1500)
    }

    private fun animateWeather() {
        val tCityName = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.transition_city_name)
        val tCityDesc = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.transition_city_details)
        val tCityRecyclers = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.transition_city_forecast)

        TransitionManager.beginDelayedTransition(binding.cityNameConstraint, tCityName)
        TransitionManager.beginDelayedTransition(binding.conditionConstraint, tCityDesc)
        TransitionManager.beginDelayedTransition(binding.recyclersConstraint, tCityRecyclers)

        makeViewsVisible()

        Handler().postDelayed({
            if (_binding != null) {
                TransitionManager.endTransitions(binding.cityNameConstraint)
                TransitionManager.endTransitions(binding.conditionConstraint)
                TransitionManager.endTransitions(binding.recyclersConstraint)
            }
        }, 1000)
    }

    private fun makeViewsGone() {
        binding.textViewCity.visibility = View.GONE
        binding.textViewTemperature.visibility = View.GONE
        binding.textViewDescription.visibility = View.GONE
        binding.textViewHighTemperature.visibility = View.GONE
        binding.textViewLowTemperature.visibility = View.GONE
        binding.recyclerTopSeparator.visibility = View.GONE
        binding.recyclerBottomSeparator.visibility = View.GONE
        binding.recyclerHours.visibility = View.GONE
        binding.recyclerDays.visibility = View.GONE
    }

    private fun makeViewsVisible() {
        binding.textViewCity.visibility = View.VISIBLE
        binding.textViewTemperature.visibility = View.VISIBLE
        binding.textViewDescription.visibility = View.VISIBLE
        binding.textViewHighTemperature.visibility = View.VISIBLE
        binding.textViewLowTemperature.visibility = View.VISIBLE
        binding.recyclerTopSeparator.visibility = View.VISIBLE
        binding.recyclerBottomSeparator.visibility = View.VISIBLE
        binding.recyclerHours.visibility = View.VISIBLE
        binding.recyclerDays.visibility = View.VISIBLE
    }

}
