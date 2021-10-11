package com.example.android.notesapp.ui.lockscreen

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.android.notesapp.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}