package com.artemkudryavtsev.newsapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.artemkudryavtsev.newsapp.R
import com.artemkudryavtsev.newsapp.data.Article
import com.artemkudryavtsev.newsapp.data.TopHeadlines
import com.artemkudryavtsev.newsapp.database.ArticleEntity
import com.artemkudryavtsev.newsapp.database.NewsDatabase
import com.artemkudryavtsev.newsapp.database.articleAsArticleEntity
import com.artemkudryavtsev.newsapp.database.articleEntityAsArticleData
import com.artemkudryavtsev.newsapp.network.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import timber.log.Timber

class NewsAppRepository(private val database: NewsDatabase) {

    suspend fun getTopHeadlines(
        appContext: Context,
        countryCode: String
    ): TopHeadlines {
        val api = NewsApi.retrofitService
        var response = TopHeadlines()
        try {
            response =
                api.getTopHeadlines(
                    countryCode,
                    appContext.applicationContext.getString(R.string.api_key)
                )
        } catch (e: Exception) {
            Timber.d("$e")
        }

        return response
    }

    suspend fun addBookmark(article: Article) {
        withContext(Dispatchers.IO) {
            val articleEntity = articleAsArticleEntity(article)
            database.newsDao.addBookmark(articleEntity)
        }
    }

    suspend fun getBookmarks(): List<Article> {
        return withContext(Dispatchers.IO) {
            database.newsDao.getAllBookmarks()
                .filter {
                    it.isBookmark
                }
                .map {
                    articleEntityAsArticleData(it)
                }
        }
    }

    suspend fun removeFromBookmarks(article: Article): Boolean {
        return withContext(Dispatchers.IO) {
            val defferedRowsDeleted = async {
                database.newsDao.deleteBookmark(articleUrl = article.url)
            }
            val rowsDeleted = defferedRowsDeleted.await()
            if (rowsDeleted > 0) {
                return@withContext true
            }
            return@withContext false
        }
    }
}
