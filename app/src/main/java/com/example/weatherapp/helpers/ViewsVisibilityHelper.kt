package com.example.weatherapp.helpers

import android.view.View

class ViewsVisibilityHelper {

    fun makeViewsGone(views: ArrayList<View>){
        for (view in views){
            view.visibility = View.GONE
        }
    }

    fun makeViewsVisible(views: ArrayList<View>){
        for (view in views){
            view.visibility = View.VISIBLE
        }
    }
}