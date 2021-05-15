package com.artemkudryavtsev.newsapp.settings.languageandregion

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.artemkudryavtsev.newsapp.R
import com.artemkudryavtsev.newsapp.databinding.FragmentLanguageAndRegionBinding

class LanguageAndRegionFragment : Fragment() {
    private lateinit var binding: FragmentLanguageAndRegionBinding
    private lateinit var adapter: LanguageAndRegionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.sharedPreferenceFile), Context.MODE_PRIVATE
        )

        val countryCode = sharedPref?.getString(
            getString(R.string.countryCodeKey),
            getString(R.string.defaultCountryCodeKey)
        ) ?: getString(R.string.defaultCountryCodeKey)

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_language_and_region,
            container,
            false
        )

        adapter = LanguageAndRegionAdapter(countryCode, LanguageAndRegionAdapter.OnClickListener {
            sharedPref?.edit {
                putString(getString(R.string.countryCodeKey), it.first)
            }
            updateAdapterData(it.first)
        })
        binding.languageAndRegionRecyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.setupWithNavController(findNavController())
        binding.topAppBar.title =
            resources.getString(R.string.language_and_region_appbar_title)
    }

    private fun updateAdapterData(countryCode: String) {
        adapter.notifyDataSetChanged()
        adapter.countryCode = countryCode
    }
}
