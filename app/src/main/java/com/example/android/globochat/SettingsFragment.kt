package com.example.android.globochat

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.*


class SettingsFragment : PreferenceFragmentCompat() {

    var TAG = "SettingsFragment"

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        val accSettingsPref = findPreference<Preference>(getString(R.string.key_account_settings))

        accSettingsPref?.setOnPreferenceClickListener {
            val navHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_frag) as NavHostFragment
            val navController = navHostFragment.navController
            val action = SettingsFragmentDirections.actionSettingsToAccSettings()
            navController.navigate(action)
            true
        }

        val sharedPreferences  = PreferenceManager.getDefaultSharedPreferences(context)
        val autoReplyValue = sharedPreferences.getString(getString(R.string.key_auto_reply_msg), "")
        Log.d(TAG, "this is user auto reply value : $autoReplyValue")
        val autoDownload = sharedPreferences.getBoolean(getString(R.string.key_auto_download), false)
        Log.d(TAG, "this is user auto download value : $autoDownload")

        val statusPref = findPreference<EditTextPreference>(getString(R.string.key_status))
        // called before Preference change
        statusPref?.setOnPreferenceChangeListener { preference, newValue ->

            val newStatus = newValue as String
            if (newStatus.contains("bad")) {
                Toast.makeText(context, "Inappropriate Status. Please maintain community guidelines.",
                    Toast.LENGTH_SHORT).show()

                false   // false: reject the new value.
            } else {
                true     // true: accept the new value.
            }
        }
        val notificationPref = findPreference<SwitchPreferenceCompat>(getString(R.string.key_new_msg_notif))
        notificationPref?.summaryProvider = Preference.SummaryProvider<SwitchPreferenceCompat> { switchPref ->
            if(switchPref?.isChecked!!)
                "Status : ON"
            else
                "Status : OFF"

        }
    }
}