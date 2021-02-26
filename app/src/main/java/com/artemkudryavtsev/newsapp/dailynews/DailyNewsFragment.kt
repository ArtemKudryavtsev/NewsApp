package com.artemkudryavtsev.newsapp.dailynews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        viewModel.getCurrentData()

        viewModel.dataResponse.observe(viewLifecycleOwner, {
            it?.let {
                adapter.articles = it.articles
            }
        })

        adapter = DailyNewsAdapter(DailyNewsAdapter.OnClickListener {
            viewModel.displayNewsDetails(it)
        })
        binding.dailyNewsRecyclerView.adapter = adapter

        viewModel.navigateToNewsDetails.observe(viewLifecycleOwner, {
            it?.let {
                findNavController()
                    .navigate(DailyNewsFragmentDirections.actionDailyNewsFragmentToNewsDetailsFragment(it))
            }
        })

        return binding.root
    }
}
