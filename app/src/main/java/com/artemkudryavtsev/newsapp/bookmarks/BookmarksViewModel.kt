package com.artemkudryavtsev.newsapp.bookmarks

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.artemkudryavtsev.newsapp.data.Article
import com.artemkudryavtsev.newsapp.database.getDataBase
import com.artemkudryavtsev.newsapp.repository.NewsAppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookmarksViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = NewsAppRepository(getDataBase(app))

    private val _bookmarksArticles: MutableLiveData<List<Article>> = MutableLiveData()
    val bookmarksArticles: LiveData<List<Article>> = _bookmarksArticles

    private val _navigateToNewsDetails: MutableLiveData<Article> = MutableLiveData()
    val navigateToNewsDetails: LiveData<Article> = _navigateToNewsDetails

    fun getBookmarks() {
        viewModelScope.launch {
            _bookmarksArticles.value = repository.getAllBookmarks()
        }
    }

    fun displayNewsDetails(article: Article) {
        _navigateToNewsDetails.value = article
    }

    fun doneDisplayNewsDetails() {
        _navigateToNewsDetails.value = null
    }

    fun removeFromBookmarks(article: Article) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                val defferedResultOfRemoving = async {
                    repository.removeFromBookmarks(article)
                }
                val resultOfRemoving = defferedResultOfRemoving.await()
                withContext(Dispatchers.Main) {
                    if (resultOfRemoving) {
                        getBookmarks()
                    }
                }
            }
        }
    }
}
