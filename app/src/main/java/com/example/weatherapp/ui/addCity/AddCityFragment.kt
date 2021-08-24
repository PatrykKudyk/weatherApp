package com.example.weatherapp.ui.addCity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentAddCityBinding
import com.example.weatherapp.db.city.City
import kotlin.random.Random

class AddCityFragment : Fragment() {

    private lateinit var viewModel: AddCityViewModel
    private var _binding: FragmentAddCityBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(AddCityViewModel::class.java)
        _binding = FragmentAddCityBinding.inflate(inflater, container, false)

        binding.addCityButton.setOnClickListener {
            viewModel.hideKeyboard((context as MainActivity))
            val givenCityName = binding.cityNameEditText.text.toString()

            if (viewModel.canPressButton(givenCityName)) {
                viewModel.isRequestDone.value = false
                viewModel.performAddCityButtonClick(givenCityName)

                if (!viewModel.isRequestDone.hasActiveObservers())
                    viewModel.isRequestDone.observe(viewLifecycleOwner, Observer {

                        when (viewModel.isCityCorrect) {
                            null -> {
                                if (viewModel.isRequestDone.value == true)
                                    showSimpleToast(requireContext().getString(R.string.toast_something_went_wrong_try_again))
                            }

                            false -> {
                                if (viewModel.isRequestDone.value == true)
                                    showSimpleToast(requireContext().getString(R.string.cityDoesNotExist))
                            }

                            true -> {
                                if (viewModel.isRequestDone.value == true) {
                                    if (viewModel.fetchPrimaryCity() != null) {
                                        viewModel.updateAsync(viewModel.chosenCity)
                                    } else {
                                        viewModel.insertAsync(
                                            City(
                                                Random.nextInt(),
                                                viewModel.chosenCity,
                                                "Poland",
                                                true
                                            )
                                        )
                                    }
                                    requireActivity().runOnUiThread {
                                        findNavController().navigate(R.id.action_add_city_to_nav_home)
                                    }
                                }
                            }
                        }

                    })
            } else {
                showSimpleToast(requireContext().getString(R.string.noCityGiven))
            }
        }

        return binding.root
    }

    private fun showSimpleToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}