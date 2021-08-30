package com.example.weatherapp.controls

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.weatherapp.R
import com.example.weatherapp.R.layout.control_information_view

class InformationControlView @JvmOverloads
constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    var titleTextView: TextView
    var descriptionTextView: TextView

    init {
        orientation = VERTICAL
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.control_information_view, this)
        titleTextView = view.findViewById(R.id.titleTextView)
        descriptionTextView = view.findViewById(R.id.descriptionTextView)
    }

    fun fillFields(title: String, description: String){
        titleTextView.text = title
        descriptionTextView.text = description
    }
}