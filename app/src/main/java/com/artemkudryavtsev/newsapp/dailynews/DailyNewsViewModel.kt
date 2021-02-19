package com.artemkudryavtsev.newsapp.dailynews

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.artemkudryavtsev.newsapp.R
import com.artemkudryavtsev.newsapp.data.TopHeadlines
import com.artemkudryavtsev.newsapp.network.NewsApi
import kotlinx.coroutines.launch
import timber.log.Timber

class DailyNewsViewModel(
    val app: Application
) : AndroidViewModel(app) {

    private val _dataResponse: MutableLiveData<TopHeadlines> = MutableLiveData()
    val dataResponse: LiveData<TopHeadlines> = _dataResponse

    fun getCurrentData() {
        val api = NewsApi.retrofitService

        viewModelScope.launch {
            try {
                val response =
                    api.getTopHeadlines("us", app.applicationContext.getString(R.string.api_key))
                _dataResponse.value = response
            } catch (e: Exception) {
                Timber.d("$e")
            }
        }
    }
}