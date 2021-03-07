package com.artemkudryavtsev.newsapp.dailynews

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.artemkudryavtsev.newsapp.R
import com.artemkudryavtsev.newsapp.databinding.FragmentDailyNewsBinding

class DailyNewsFragment : Fragment() {

    private lateinit var binding: FragmentDailyNewsBinding
    private lateinit var viewModel: DailyNewsViewModel
    private lateinit var adapter: DailyNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_daily_news, container, false)

        val app = requireActivity().application
        val viewModelFactory = DailyNewsViewModelFactory(app)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(DailyNewsViewModel::class.java)

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.sharedPreferenceFile), Context.MODE_PRIVATE
        )

        val countryCode = sharedPref?.getString(
            getString(R.string.countryCodeKey),
            getString(R.string.defaultCountryCodeKey)
        ) ?: getString(R.string.defaultCountryCodeKey)

        viewModel.getCurrentData(countryCode)

        adapter = DailyNewsAdapter(DailyNewsAdapter.OnClickListener {
            viewModel.displayNewsDetails(it)
        })
        binding.dailyNewsRecyclerView.adapter = adapter

        viewModel.dataResponse.observe(viewLifecycleOwner, {
            it?.let {
                adapter.articles = it.articles
            }
        })

        viewModel.navigateToNewsDetails.observe(viewLifecycleOwner, {
            it?.let {
                findNavController()
                    .navigate(DailyNewsFragmentDirections.actionDailyNewsFragmentToNewsDetailsFragment(it))
            }
        })

        return binding.root
    }
}
