package com.artemkudryavtsev.newsapp.dailynews

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DailyNewsViewModelFactory(
    private val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyNewsViewModel::class.java)) {
            return DailyNewsViewModel(app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}