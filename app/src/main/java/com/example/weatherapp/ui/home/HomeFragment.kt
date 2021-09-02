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
import com.example.weatherapp.models.enums.OrderOwnerEnum

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


        viewModel.initTransitionHelper(binding, requireContext())

        viewModel.primaryCity.observe(requireActivity(), Observer { city ->
            city?.let {
                cityName = city.name
                checkPrimaryCity()
            }
        })
        checkPrimaryCity()
        initListeners()

        return root
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({
            makeStartAnimations()
        }, 100)
    }

    override fun onStop() {
        viewModel.transitionHelper.prepareViewsForClosingFragment()
        viewModel.shouldAnimate = true
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.primaryCity.removeObservers(requireActivity())
        _binding = null
    }

    private fun checkPrimaryCity() {
        if (cityName.isEmpty()) {
            binding.addCityGroup.visibility = View.VISIBLE
            binding.showWeatherGroup.visibility = View.GONE

        } else {
            binding.textViewCity.text = cityName
            viewModel.fetchForecastData(cityName, requireActivity(), binding)

            binding.addCityGroup.visibility = View.GONE
            binding.showWeatherGroup.visibility = View.VISIBLE

            Handler().postDelayed({
                makeStartAnimations()
            }, 100)
        }
    }

    private fun initListeners() {
        binding.fabSwap.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("order_owner", OrderOwnerEnum.HOME_FRAGMENT)
            findNavController().navigate(R.id.action_nav_home_to_add_city, bundle)
        }

        binding.imageAddCity.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("order_owner", OrderOwnerEnum.HOME_FRAGMENT)
            findNavController().navigate(R.id.action_nav_home_to_add_city, bundle)
        }

        binding.fabDetails.setOnClickListener {
            viewModel.handleFabDetailsClick()
        }
    }

    private fun makeStartAnimations() {
        if (viewModel.shouldAnimate && binding.mainConstraint.visibility == View.VISIBLE) {
            viewModel.transitionHelper.makeStartTransitions()
            viewModel.shouldAnimate = false
        }
    }

}
