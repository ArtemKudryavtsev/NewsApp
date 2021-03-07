package com.artemkudryavtsev.newsapp.settings.languageandregion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.artemkudryavtsev.newsapp.R

class LanguageAndRegionAdapter(
    initialCountryCode: String,
    private val clickListener: OnClickListener
) :
    RecyclerView.Adapter<LanguageAndRegionAdapter.LanguageAndRegionViewHolder>() {

    private val countries: List<Pair<String, String>> = Countries.countries
    var countryCode: String = initialCountryCode

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageAndRegionViewHolder {
        return LanguageAndRegionViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_language_and_region, parent, false)
        )
    }

    override fun onBindViewHolder(holder: LanguageAndRegionViewHolder, position: Int) {
        val country = countries[position]
        val isChecked = country.first == countryCode

        holder.bind(country.second, isChecked)
        holder.itemView.setOnClickListener {
            clickListener.onClick(country)
        }
    }

    override fun getItemCount() = countries.size

    class LanguageAndRegionViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(country: String, isChecked: Boolean) {
            view.findViewById<TextView>(R.id.languageAndRegionText).text = country
            when {
                isChecked -> {
                    view.findViewById<ImageView>(R.id.regionChecked).visibility = View.VISIBLE
                }
                else -> {
                    view.findViewById<ImageView>(R.id.regionChecked).visibility = View.INVISIBLE
                }
            }
        }
    }

    class OnClickListener(val clickListener: (country: Pair<String, String>) -> Unit) {
        fun onClick(country: Pair<String, String>) = clickListener(country)
    }
}
