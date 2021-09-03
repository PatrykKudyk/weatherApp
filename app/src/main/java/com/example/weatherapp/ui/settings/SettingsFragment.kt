package com.example.weatherapp.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.weatherapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}