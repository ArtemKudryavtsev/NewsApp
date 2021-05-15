package com.artemkudryavtsev.newsapp.settings.articletextsize

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
import com.artemkudryavtsev.newsapp.databinding.FragmentArticleTextSizeBinding
import timber.log.Timber

class ArticleTextSizeFragment : Fragment() {
    private lateinit var binding: FragmentArticleTextSizeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_text_size, container, false)

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.sharedPreferenceFile), Context.MODE_PRIVATE
        )

        val articleTextSize = sharedPref?.getString(
            getString(R.string.article_text_size_key),
            getString(R.string.normal)
        ) ?: getString(R.string.normal)

        when (articleTextSize) {
            getString(R.string.small) -> {
                binding.smallTextChecked.visibility = View.VISIBLE
            }

            getString(R.string.large) -> {
                binding.largeTextChecked.visibility = View.VISIBLE
            }
            else -> {
                binding.normalTextChecked.visibility = View.VISIBLE
            }
        }

        binding.smallTextContainer.setOnClickListener {
            binding.smallTextChecked.visibility = View.VISIBLE
            binding.normalTextChecked.visibility = View.INVISIBLE
            binding.largeTextChecked.visibility = View.INVISIBLE

            sharedPref?.edit {
                putString(getString(R.string.article_text_size_key), getString(R.string.small))
            }
        }

        binding.normalTextContainer.setOnClickListener {
            binding.normalTextChecked.visibility = View.VISIBLE
            binding.smallTextChecked.visibility = View.INVISIBLE
            binding.largeTextChecked.visibility = View.INVISIBLE

            sharedPref?.edit {
                putString(getString(R.string.article_text_size_key), getString(R.string.normal))
            }
        }

        binding.largeTextContainer.setOnClickListener {
            binding.largeTextChecked.visibility = View.VISIBLE
            binding.smallTextChecked.visibility = View.INVISIBLE
            binding.normalTextChecked.visibility = View.INVISIBLE

            sharedPref?.edit {
                putString(getString(R.string.article_text_size_key), getString(R.string.large))
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.setupWithNavController(findNavController())
        binding.topAppBar.title = resources.getString(R.string.article_text_size_appbar_title)
    }
}
