package com.artemkudryavtsev.newsapp.bookmarks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.artemkudryavtsev.newsapp.R
import com.artemkudryavtsev.newsapp.newsbottomsheetdialog.NewsBottomSheetDialogFragment
import com.artemkudryavtsev.newsapp.databinding.FragmentBookmarksBinding

class BookmarksFragment : Fragment() {
    private lateinit var adapter: BookmarksAdapter
    private lateinit var binding: FragmentBookmarksBinding
    private lateinit var viewModel: BookmarksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bookmarks, container, false)

        val app = requireActivity().application
        val viewModelFactory = BookmarksViewModelFactory(app)
        viewModel = ViewModelProvider(this, viewModelFactory).get(BookmarksViewModel::class.java)

        adapter = BookmarksAdapter(
            BookmarksAdapter.OnBookmarksClickListener(
                {
                    viewModel.displayNewsDetails(it)
                    viewModel.doneDisplayNewsDetails()
                },
                {
                    viewModel.removeFromBookmarks(it)
                },
                {
                    NewsBottomSheetDialogFragment(
                        it,
                        NewsBottomSheetDialogFragment.FragmentType.BOOKMARKS
                    ).show(
                        parentFragmentManager,
                        "bottomSheetDialog"
                    )
                })
        )
        binding.bookmarksRecyclerView.adapter = adapter

        viewModel.getBookmarks()

        viewModel.bookmarksArticles.observe(viewLifecycleOwner, {
            adapter.bookmarkArticles = it
        })

        viewModel.navigateToNewsDetails.observe(viewLifecycleOwner, {
            it?.let {
                findNavController().navigate(
                    BookmarksFragmentDirections.actionBookmarksFragmentToNewsDetailsFragment(it)
                )
            }
        })

        return binding.root
    }
}
