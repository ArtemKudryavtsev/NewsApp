package com.artemkudryavtsev.newsapp.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class TopHeadlines(
    val status: String = "",
    val totalResults: Int = 0,
    val articles: List<Article> = listOf()
)

@Parcelize
data class Article(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    var isBookmark: Boolean = false
) : Parcelable

@Parcelize
data class Source(val id: String?, val name: String?) : Parcelable
