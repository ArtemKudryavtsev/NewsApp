package com.artemkudryavtsev.newsapp.bookmarks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artemkudryavtsev.newsapp.R
import com.artemkudryavtsev.newsapp.data.Article
import com.artemkudryavtsev.newsapp.util.publishAtFormat
import com.squareup.picasso.Picasso

class BookmarksAdapter(private val bookmarkClickListener: OnBookmarksClickListener) :
    RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder>() {
    var bookmarkArticles = listOf<Article>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarksViewHolder {
        return BookmarksViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BookmarksViewHolder, position: Int) {
        val bookmarkArticle = bookmarkArticles[position]
        holder.bind(bookmarkArticle)

        holder.itemView.findViewById<ImageView>(R.id.newsItemBoomark).setOnClickListener {
            bookmarkClickListener.onRemoveFromBookmarksClicked(bookmarkArticle)
        }

        holder.itemView.setOnClickListener {
            bookmarkClickListener.onItemClicked(bookmarkArticle)
        }

        holder.itemView.findViewById<ImageView>(R.id.newsItemMore).setOnClickListener {
            bookmarkClickListener.onShowMoreOptionsClicked(bookmarkArticle)
        }
    }

    override fun getItemCount(): Int {
        return bookmarkArticles.size
    }

    class BookmarksViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(article: Article) {
            view.findViewById<TextView>(R.id.newsTitle).text = article.title
            view.findViewById<TextView>(R.id.newsSourceName).text = article.source?.name
            view.findViewById<TextView>(R.id.newsPublishedDate).text =
                publishAtFormat(article.publishedAt)
            Picasso
                .get()
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_broken_image)
                .error(R.drawable.ic_connection_error)
                .into(view.findViewById<ImageView>(R.id.newsSmallImage))
            view.findViewById<ImageView>(R.id.newsItemBoomark)
                .setImageResource(R.drawable.ic_bookmark_black)
        }
    }

    class OnBookmarksClickListener(
        val itemClickListener: (article: Article) -> Unit,
        val removeFromBookmarksListener: (article: Article) -> Unit,
        val showMoreOptions: (article: Article) -> Unit
    ) {
        fun onItemClicked(article: Article) = itemClickListener(article)

        fun onRemoveFromBookmarksClicked(article: Article) = removeFromBookmarksListener(article)

        fun onShowMoreOptionsClicked(article: Article) = showMoreOptions(article)
    }
}
