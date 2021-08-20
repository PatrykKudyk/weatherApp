package com.example.weatherapp.ui.addCity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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
            when (viewModel.canPressButton(givenCityName)) {
                AddCityViewModel.CITY_EXISTS -> {
                    if (viewModel.fetchPrimaryCity() != null) {
                        viewModel.updateAsync(givenCityName)
                    } else {
                        viewModel.insertAsync(
                            City(
                                Random.nextInt(),
                                givenCityName,
                                "Poland",
                                true
                            )
                        )
                    }
                    findNavController().navigate(R.id.action_add_city_to_nav_home)
                }

                AddCityViewModel.NO_CITY_GIVEN -> showSimpleToast(requireContext().getString(R.string.noCityGiven))

                AddCityViewModel.CITY_DOES_NOT_EXIST -> showSimpleToast(requireContext().getString(R.string.cityDoesNotExist))
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