package com.artemkudryavtsev.newsapp.newsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.artemkudryavtsev.newsapp.R
import com.squareup.picasso.Picasso

class NewsDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val article = NewsDetailsFragmentArgs.fromBundle(requireArguments()).newsDetails

        Picasso
            .get()
            .load(article.urlToImage)
            .placeholder(R.drawable.ic_broken_image)
            .error(R.drawable.ic_connection_error)
            .into(view.findViewById<ImageView>(R.id.newsDetailsImage))

        view.findViewById<TextView>(R.id.newsDetailsContent).text = article.content
        view.findViewById<TextView>(R.id.newsDetailsTitle).text = article.title
        view.findViewById<TextView>(R.id.newsDetailsPublishedDate).text = article.publishedAt
    }
}