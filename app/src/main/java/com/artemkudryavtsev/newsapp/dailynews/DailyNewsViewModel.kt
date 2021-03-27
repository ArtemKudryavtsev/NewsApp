package com.artemkudryavtsev.newsapp.dailynews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.artemkudryavtsev.newsapp.data.Article
import com.artemkudryavtsev.newsapp.data.TopHeadlines
import com.artemkudryavtsev.newsapp.database.getDataBase
import com.artemkudryavtsev.newsapp.repository.NewsAppRepository
import kotlinx.coroutines.launch

class DailyNewsViewModel(
    private val app: Application
) : AndroidViewModel(app) {

    private val repository = NewsAppRepository(getDataBase(app))

    private val _dataResponse: MutableLiveData<TopHeadlines> = MutableLiveData()
    val dataResponse: LiveData<TopHeadlines> = _dataResponse

    private val _navigateToNewsDetails: MutableLiveData<Article> = MutableLiveData()
    val navigateToNewsDetails: LiveData<Article> = _navigateToNewsDetails

    fun getCurrentData(countryCode: String) {
        viewModelScope.launch {
            _dataResponse.value = repository.getTopHeadlines(app, countryCode)
        }
    }

    fun displayNewsDetails(article: Article) {
        _navigateToNewsDetails.value = article
    }

    fun doneDisplayNewsDetails() {
        _navigateToNewsDetails.value = null
    }

    fun addItemToBoomarks(article: Article) {
        article.isBookmark = true
        viewModelScope.launch {
            repository.addToBookmarks(article)
        }
    }
}
