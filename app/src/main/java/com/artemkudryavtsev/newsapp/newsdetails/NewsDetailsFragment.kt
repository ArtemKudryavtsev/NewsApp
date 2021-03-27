package com.artemkudryavtsev.newsapp.newsdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.artemkudryavtsev.newsapp.R
import com.artemkudryavtsev.newsapp.data.Article
import com.artemkudryavtsev.newsapp.databinding.FragmentNewsDetailsBinding
import com.artemkudryavtsev.newsapp.util.publishAtFormat
import com.squareup.picasso.Picasso

class NewsDetailsFragment : Fragment() {
    private lateinit var binding: FragmentNewsDetailsBinding
    private lateinit var article: Article
    private lateinit var viewModel: NewsDetailsViewModel
    private var isArticle: Boolean = false

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

        Picasso
            .get()
            .load(article.urlToImage)
            .placeholder(R.drawable.ic_broken_image)
            .error(R.drawable.ic_connection_error)
            .into(binding.newsDetailsImage)

        binding.newsDetailsContent.text =
            article.content
        binding.newsDetailsTitle.text = article.title
        binding.newsDetailsPublishedDate.text = publishAtFormat(article.publishedAt)

        binding.urlText.apply {
            text = article.url
            setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse(article.url)
                startActivity(browserIntent)
            }
        }

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            menuItem?.let {
                when (it.itemId) {
                    R.id.shareItemMenuNewsDetails -> share()
                    R.id.bookmarkItemMenuNewsDetails -> bookmarkTheArticle()
                }
            }
            true
        }

        viewModel.isInBoomarks(article)

        viewModel.isBookmark.observe(viewLifecycleOwner, { isBoomark ->
            isBoomark?.let {
                isArticle = when {
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
    }

    private fun share() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, article.url)
        val title = resources.getString(R.string.share)
        val shareIntentWithChooser = Intent.createChooser(shareIntent, title)
        if (shareIntentWithChooser.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(shareIntentWithChooser)
        }
    }

    private fun bookmarkTheArticle() {
        if (isArticle) {
            viewModel.removeFromBookmarks(article)
        } else {
            viewModel.addToBoomarks(article)
        }
    }
}
