package com.artemkudryavtsev.newsapp.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.artemkudryavtsev.newsapp.R
import com.artemkudryavtsev.newsapp.databinding.FragmentSettingsBinding
import com.artemkudryavtsev.newsapp.util.getCountryByCode

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)

        binding.settingLanguageAndRegion.setOnClickListener {
            findNavController()
                .navigate(SettingsFragmentDirections.actionSettingsFragmentToLanguageAndRegionFragment())
        }

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.sharedPreferenceFile), Context.MODE_PRIVATE
        )

        val countryCode = sharedPref?.getString(
            getString(R.string.countryCodeKey),
            getString(R.string.defaultCountryCodeKey)
        ) ?: getString(R.string.defaultCountryCodeKey)

        binding.settingLanguageAndRegionCurrent.text = getCountryByCode(countryCode)

        return binding.root
    }
}
