package com.example.weatherapp.ui.home

import android.content.Context
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.view.View
import android.widget.ScrollView
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.helpers.ViewsVisibilityHelper

class HomeFragmentTransitionHelper(private val binding: FragmentHomeBinding?, context: Context) {
    private val basicTransitionViews: ArrayList<View>
    private val recyclersConstraint: ConstraintLayout
    private val detailsConstraint: ConstraintLayout
    private val detailsScrollView: ScrollView
    private val visibilityHelper: ViewsVisibilityHelper
    private val tBackground: Transition
    private val tCityName: Transition
    private val tCityDesc: Transition
    private val tCityRecyclers: Transition
    private val tSwipeOut: Transition
    private val tSwipeIn: Transition

    init {
        basicTransitionViews = initBasicTransitionViews()
        recyclersConstraint = binding!!.recyclersConstraint
        detailsConstraint = binding.detailsConstraint
        detailsScrollView = binding.detailsScrollView
        visibilityHelper = ViewsVisibilityHelper()
        tBackground = TransitionInflater.from(context)
            .inflateTransition(R.transition.transition_weather_background_showup)
        tCityName =
            TransitionInflater.from(context).inflateTransition(R.transition.transition_city_name)
        tCityDesc =
            TransitionInflater.from(context).inflateTransition(R.transition.transition_city_details)
        tCityRecyclers = TransitionInflater.from(context)
            .inflateTransition(R.transition.transition_city_forecast)
        tSwipeOut =
            TransitionInflater.from(context).inflateTransition(R.transition.transition_swipe_out)
        tSwipeIn =
            TransitionInflater.from(context).inflateTransition(R.transition.transition_swipe_in)
    }

    private fun initBasicTransitionViews(): ArrayList<View> {
        return arrayListOf(
            binding!!.backgroundView,
            binding.textViewCity,
            binding.textViewTemperature,
            binding.textViewDescription,
            binding.textViewHighTemperature,
            binding.textViewLowTemperature,
            binding.recyclerTopSeparator,
            binding.recyclerBottomSeparator,
            binding.recyclerHours,
            binding.recyclerDays,
            binding.fabDetails,
            binding.fabSwap,
            binding.detailsConstraint
        )
    }

    fun makeStartTransitions() {
        visibilityHelper.makeViewsGone(basicTransitionViews)

        beginDelayedStartTransitions()

        Handler().postDelayed({
            if (binding != null) {
                endDelayedStartTransitions()
            }
        }, 1400)

        visibilityHelper.makeViewsVisible(basicTransitionViews)
    }

    private fun endDelayedStartTransitions() {
        TransitionManager.endTransitions(binding?.cityNameConstraint)
        TransitionManager.endTransitions(binding?.conditionConstraint)
        TransitionManager.endTransitions(binding?.recyclersConstraint)
        TransitionManager.endTransitions(binding?.backgroundConstraint)
    }

    private fun beginDelayedStartTransitions() {
        TransitionManager.beginDelayedTransition(binding?.backgroundConstraint, tBackground)
        TransitionManager.beginDelayedTransition(binding?.cityNameConstraint, tCityName)
        TransitionManager.beginDelayedTransition(binding?.conditionConstraint, tCityDesc)
        TransitionManager.beginDelayedTransition(binding?.fabsConstraint, tCityDesc)
        TransitionManager.beginDelayedTransition(binding?.recyclersConstraint, tCityRecyclers)
    }

    fun prepareViewsForClosingFragment() {
        visibilityHelper.makeViewsGone(basicTransitionViews)
    }

    fun animateHidingDetails(){

        TransitionManager.beginDelayedTransition(recyclersConstraint, tSwipeIn)
        TransitionManager.beginDelayedTransition(detailsConstraint, tSwipeOut)

        recyclersConstraint.visibility = View.VISIBLE
        detailsScrollView.visibility = View.GONE

        Handler().postDelayed({
            if (binding != null) {
                TransitionManager.endTransitions(recyclersConstraint)
                TransitionManager.endTransitions(detailsConstraint)
            }
        }, 500)
    }

    fun animateShowingDetails(){
        TransitionManager.beginDelayedTransition(recyclersConstraint, tSwipeOut)
        TransitionManager.beginDelayedTransition(detailsConstraint, tSwipeIn)

        recyclersConstraint.visibility = View.GONE
        detailsScrollView.visibility = View.VISIBLE

        Handler().postDelayed({
            if (binding != null) {
                TransitionManager.endTransitions(recyclersConstraint)
                TransitionManager.endTransitions(detailsConstraint)
            }
        }, 500)
    }
}