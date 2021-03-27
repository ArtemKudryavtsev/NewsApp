package com.artemkudryavtsev.newsapp.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewsDao {
    @Query("SELECT * FROM ArticleEntity WHERE url = :articleUrl")
    fun getBookmark(articleUrl: String): ArticleEntity?

    @Query("SELECT * FROM ArticleEntity")
    fun getAllBookmarks(): List<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addToBookmarks(articleEntity: ArticleEntity)

    @Query("DELETE FROM ArticleEntity WHERE url = :articleUrl")
    fun removeFromBookmarks(articleUrl: String): Int
}

@Database(entities = [ArticleEntity::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
}

private lateinit var INSTANCE: NewsDatabase

fun getDataBase(context: Context): NewsDatabase {
    synchronized(NewsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java,
                "news"
            ).build()
        }
    }
    return INSTANCE
}
