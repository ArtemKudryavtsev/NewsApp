package com.artemkudryavtsev.newsapp.newsbottomsheetdialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.artemkudryavtsev.newsapp.R
import com.artemkudryavtsev.newsapp.data.Article
import com.artemkudryavtsev.newsapp.databinding.DailyNewsBottomSheetDialogBinding
import com.artemkudryavtsev.newsapp.newsdetails.NewsDetailsViewModel
import com.artemkudryavtsev.newsapp.newsdetails.NewsDetailsViewModelFactory
import com.artemkudryavtsev.newsapp.util.openTheUrl
import com.artemkudryavtsev.newsapp.util.share
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewsBottomSheetDialogFragment(val article: Article, val fragmentType: FragmentType) :
    BottomSheetDialogFragment() {
    private lateinit var binding: DailyNewsBottomSheetDialogBinding
    private lateinit var viewModel: NewsDetailsViewModel
    private var isBookmark: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.daily_news_bottom_sheet_dialog,
            container,
            false
        )

        val app = requireActivity().application
        val viewModelFactory = NewsDetailsViewModelFactory(app)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsDetailsViewModel::class.java)

        if (fragmentType == FragmentType.BOOKMARKS) {
            binding.bookmarkItem.visibility = View.GONE
        } else {
            binding.bookmarkItem.setOnClickListener {
                if (isBookmark) {
                    viewModel.removeFromBookmarks(article)
                } else {
                    viewModel.addToBoomarks(article)
                }
            }
        }

        binding.shareItem.setOnClickListener {
            share(requireContext(), article)
        }

        binding.goToItem.setOnClickListener {
            openTheUrl(requireContext(), article)
        }

        viewModel.isInBoomarks(article)

        viewModel.isBookmark.observe(viewLifecycleOwner, { isBoomark ->
            isBoomark?.let {
                isBookmark = when {
                    it -> {
                        binding.bookmarkImage.setImageResource(R.drawable.ic_bookmark_black)
                        binding.bookmarkText.text = getString(R.string.remove_from_bookmarks)
                        true
                    }
                    else -> {
                        binding.bookmarkImage.setImageResource(R.drawable.ic_bookmark_white)
                        binding.bookmarkText.text = getString(R.string.add_to_bookmarks)
                        false
                    }
                }
            }
        })

        return binding.root
    }

    enum class FragmentType {
        DAILY_NEWS,
        BOOKMARKS
    }
}
