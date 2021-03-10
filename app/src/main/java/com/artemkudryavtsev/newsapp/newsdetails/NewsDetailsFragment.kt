package com.artemkudryavtsev.newsapp.newsdetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.artemkudryavtsev.newsapp.R
import com.artemkudryavtsev.newsapp.databinding.FragmentNewsDetailsBinding
import com.squareup.picasso.Picasso

class NewsDetailsFragment : Fragment() {
    lateinit var binding: FragmentNewsDetailsBinding
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = NewsDetailsFragmentArgs.fromBundle(requireArguments()).newsDetails

        Picasso
            .get()
            .load(article.urlToImage)
            .placeholder(R.drawable.ic_broken_image)
            .error(R.drawable.ic_connection_error)
            .into(binding.newsDetailsImage)

        binding.newsDetailsContent.text =
            article.content
        binding.newsDetailsTitle.text = article.title
        binding.newsDetailsPublishedDate.text = article.publishedAt

        binding.urlText.apply {
            text = article.url
            setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW)
                browserIntent.data = Uri.parse(article.url)
                startActivity(browserIntent)
            }
        }
    }
}
