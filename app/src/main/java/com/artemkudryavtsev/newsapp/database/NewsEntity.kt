package com.artemkudryavtsev.newsapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.artemkudryavtsev.newsapp.data.Article
import com.artemkudryavtsev.newsapp.data.Source

@Entity
data class ArticleEntity(
    val author: String?,
    val title: String?,
    val description: String?,
    @PrimaryKey
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    val sourceId: String?,
    val sourceName: String?,
    val isBookmark: Boolean
)

fun articleEntityAsArticleData(articleEntity: ArticleEntity): Article {
    return Article(
        source = Source(id = articleEntity.sourceId, name = articleEntity.sourceName),
        author = articleEntity.author,
        title = articleEntity.title,
        description = articleEntity.description,
        url = articleEntity.url,
        urlToImage = articleEntity.urlToImage,
        publishedAt = articleEntity.publishedAt,
        content = articleEntity.content,
        isBookmark = articleEntity.isBookmark
    )
}

fun articleAsArticleEntity(article: Article): ArticleEntity {
    return ArticleEntity(
        author = article.author,
        title = article.title,
        description = article.description,
        url = article.url,
        urlToImage = article.urlToImage,
        publishedAt = article.publishedAt,
        content = article.content,
        sourceId = article.source?.id,
        sourceName = article.source?.name,
        isBookmark = article.isBookmark
    )
}
