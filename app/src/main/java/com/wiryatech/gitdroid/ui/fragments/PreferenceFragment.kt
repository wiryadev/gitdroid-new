package com.wiryatech.gitdroid.ui.fragments

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.wiryatech.gitdroid.R
import com.wiryatech.gitdroid.ui.activities.AboutActivity
import com.wiryatech.gitdroid.utils.ReminderReceiver

class PreferenceFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var reminderReceiver: ReminderReceiver
    private lateinit var reminderKey: String
    private lateinit var reminderPreference: SwitchPreferenceCompat
    private lateinit var aboutPreference: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        reminderReceiver = ReminderReceiver()
        reminderKey = getString(R.string.reminder_key)

        requireContext()
        initUI()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sp: SharedPreferences, key: String) {
        if (key == reminderKey) {
            reminderPreference.isChecked = sp.getBoolean(reminderKey, false)
        }

        val status = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(reminderKey, false)
        
        reminderReceiver.setReminder(requireContext(), status)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        when (preference) {
            aboutPreference -> {
                val intent = Intent(requireContext(), AboutActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onPreferenceTreeClick(preference)
    }

    private fun initUI() {
        reminderPreference = findPreference<SwitchPreferenceCompat>(reminderKey) as SwitchPreferenceCompat
        val sp = preferenceManager.sharedPreferences
        reminderPreference.isChecked = sp.getBoolean(reminderKey, false)
        aboutPreference = findPreference<Preference>(getString(R.string.about)) as Preference
    }
}