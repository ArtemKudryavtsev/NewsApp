package com.artemkudryavtsev.newsapp.dailynews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artemkudryavtsev.newsapp.R
import com.artemkudryavtsev.newsapp.data.Article
import com.squareup.picasso.Picasso

class DailyNewsAdapter(val articleClickListener: OnClickListener) : RecyclerView.Adapter<DailyNewsAdapter.DailyNewsViewHolder>() {
    var articles = listOf<Article>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyNewsViewHolder {
        return DailyNewsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_daily_news, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DailyNewsViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
        holder.itemView.setOnClickListener {
            articleClickListener.clickListener(article)
        }
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    class DailyNewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(article: Article) {
            view.findViewById<TextView>(R.id.newsTitle).text = article.title
            view.findViewById<TextView>(R.id.newsSourceName).text = article.source?.name
            view.findViewById<TextView>(R.id.newsPublishedDate).text = article.publishedAt
            Picasso
                .get()
                .load(article.urlToImage)
                .placeholder(R.drawable.ic_broken_image)
                .error(R.drawable.ic_connection_error)
                .into(view.findViewById<ImageView>(R.id.newsSmallImage))
        }
    }

    class OnClickListener(val clickListener: (article: Article) -> Unit) {
        fun onClick(article: Article) = clickListener(article)
    }
}
