package com.wiryatech.gitdroid.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.wiryatech.gitdroid.R

class PreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

}