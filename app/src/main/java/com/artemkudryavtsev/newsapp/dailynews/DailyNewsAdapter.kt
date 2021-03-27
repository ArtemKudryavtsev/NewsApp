package com.artemkudryavtsev.newsapp.dailynews

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

class DailyNewsAdapter(private val articleClickListener: OnDailyNewsClickListener) :
    RecyclerView.Adapter<DailyNewsAdapter.DailyNewsViewHolder>() {
    var articles = listOf<Article>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyNewsViewHolder {
        return DailyNewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DailyNewsViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)

        holder.itemView.findViewById<ImageView>(R.id.newsItemBoomark).setOnClickListener {
            articleClickListener.onAddToBookmarksClicked(article)
        }

        holder.itemView.setOnClickListener {
            articleClickListener.onItemClicked(article)
        }

        holder.itemView.findViewById<ImageView>(R.id.newsItemMore).setOnClickListener {
            articleClickListener.onShowMoreOptionsClicked(article)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class DailyNewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
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
            view.findViewById<ImageView>(R.id.newsItemBoomark).visibility = View.GONE
        }
    }

    class OnDailyNewsClickListener(
        val itemClickListener: (article: Article) -> Unit,
        val addToBookmarksListener: (article: Article) -> Unit,
        val showMoreOptions: (article: Article) -> Unit
    ) {
        fun onItemClicked(article: Article) = itemClickListener(article)

        fun onAddToBookmarksClicked(article: Article) = addToBookmarksListener(article)

        fun onShowMoreOptionsClicked(article: Article) = showMoreOptions(article)
    }
}
