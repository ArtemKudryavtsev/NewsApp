package com.artemkudryavtsev.newsapp.newsdetails

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.artemkudryavtsev.newsapp.R
import com.artemkudryavtsev.newsapp.data.Article
import com.artemkudryavtsev.newsapp.databinding.FragmentNewsDetailsBinding
import com.artemkudryavtsev.newsapp.util.getBigImage
import com.artemkudryavtsev.newsapp.util.openTheUrl
import com.artemkudryavtsev.newsapp.util.publishAtFormat
import com.artemkudryavtsev.newsapp.util.share

class NewsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailsBinding
    private lateinit var article: Article
    private lateinit var viewModel: NewsDetailsViewModel
    private var isBookmark: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_news_details,
            container,
            false
        )
        val app = requireActivity().application
        val viewModelFactory = NewsDetailsViewModelFactory(app)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsDetailsViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        article = NewsDetailsFragmentArgs.fromBundle(requireArguments()).newsDetails

        binding.topAppBar.setupWithNavController(findNavController())
        binding.topAppBar.title = ""

        getBigImage(requireContext(), article, binding.newsDetailsImage)

        binding.newsDetailsContent.text =
            article.content
        binding.newsDetailsTitle.text = article.title
        binding.newsDetailsPublishedDate.text = publishAtFormat(article.publishedAt)

        binding.urlText.apply {
            text = article.url
            setOnClickListener {
                openTheUrl(requireContext(), article)
            }
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            menuItem?.let {
                when (it.itemId) {
                    R.id.shareItemMenuNewsDetails -> share(requireContext(), article)
                    R.id.bookmarkItemMenuNewsDetails -> bookmarkTheArticle()
                }
            }
            true
        }

        viewModel.isInBoomarks(article)

        viewModel.isBookmark.observe(viewLifecycleOwner, { isBoomark ->
            isBoomark?.let {
                isBookmark = when {
                    it -> {
                        binding.topAppBar.menu.getItem(1).setIcon(R.drawable.ic_bookmark_black)
                        true
                    }
                    else -> {
                        binding.topAppBar.menu.getItem(1).setIcon(R.drawable.ic_bookmark_white)
                        false
                    }
                }
            }
        })

        val sharedPref = activity?.getSharedPreferences(
            getString(R.string.sharedPreferenceFile), Context.MODE_PRIVATE
        )

        val articleTextSize = sharedPref?.getString(
            getString(R.string.article_text_size_key),
            getString(R.string.normal)
        ) ?: getString(R.string.normal)

        when (articleTextSize) {
            getString(R.string.small) -> {
                binding.newsDetailsContent.textSize = 12f;
            }

            getString(R.string.large) -> {
                binding.newsDetailsContent.textSize = 16f;
            }

            else -> {
                binding.newsDetailsContent.textSize = 14f;
            }
        }
    }

    private fun bookmarkTheArticle() {
        if (isBookmark) {
            viewModel.removeFromBookmarks(article)
        } else {
            viewModel.addToBoomarks(article)
        }
    }
}
