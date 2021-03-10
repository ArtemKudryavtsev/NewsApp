package com.artemkudryavtsev.newsapp.bookmarks

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.artemkudryavtsev.newsapp.dailynews.DailyNewsViewModel

class BookmarksViewModelFactory(val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarksViewModel::class.java)) {
            return BookmarksViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}