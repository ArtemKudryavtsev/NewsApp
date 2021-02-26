package com.artemkudryavtsev.newsapp.newsdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.artemkudryavtsev.newsapp.R

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
        view.findViewById<TextView>(R.id.detailsTest).text = article.title
    }
}