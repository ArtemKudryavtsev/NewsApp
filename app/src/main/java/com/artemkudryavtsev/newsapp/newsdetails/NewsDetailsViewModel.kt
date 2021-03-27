package com.artemkudryavtsev.newsapp.newsdetails

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

class NewsDetailsViewModel(
    private val app: Application
) : AndroidViewModel(app) {
    private val repository = NewsAppRepository(getDataBase(app))

    private val _isBookmark = MutableLiveData<Boolean>()
    val isBookmark: LiveData<Boolean> = _isBookmark

    fun isInBoomarks(article: Article) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val defferedResult = async { repository.getBoomark(article) }
                val result = defferedResult.await()
                withContext(Dispatchers.Main) {
                    _isBookmark.value = result != null
                }
            }
        }
    }

    fun removeFromBookmarks(article: Article) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val deffered = async {
                    repository.removeFromBookmarks(article)
                }
                deffered.await()
                withContext(Dispatchers.Main) {
                    isInBoomarks(article)
                }
            }
        }
    }

    fun addToBoomarks(article: Article) {
        article.isBookmark = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val deffered = async {
                    repository.addToBookmarks(article)
                }
                deffered.await()
                withContext(Dispatchers.Main) {
                    isInBoomarks(article)
                }
            }
        }
    }
}
