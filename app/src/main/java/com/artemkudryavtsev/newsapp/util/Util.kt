package com.artemkudryavtsev.newsapp.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import com.artemkudryavtsev.newsapp.R
import com.artemkudryavtsev.newsapp.data.Article
import com.artemkudryavtsev.newsapp.settings.languageandregion.Countries
import com.squareup.picasso.Picasso
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun getCountryByCode(countryCode: String): String {
    for (country in Countries.countries) {
        if (countryCode == country.first) {
            return country.second
        }
    }
    return "United States of America"
}

fun publishAtFormat(dateOfPublishing: String?): String? {
    if (dateOfPublishing != null) {
        val inputFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val outputFormat = "d MMMM, yyyy"

        var parsed: Date?
        var outputDate = ""
        val dfInput = SimpleDateFormat(inputFormat, Locale.getDefault())
        val dfOutput = SimpleDateFormat(outputFormat, Locale.getDefault())
        try {
            parsed = dfInput.parse(dateOfPublishing)
            outputDate = dfOutput.format(parsed)
        } catch (e: ParseException) {
            Timber.i("$e")
        }
        return outputDate
    } else {
        return dateOfPublishing
    }
}

fun share(context: Context, article: Article) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, article.url)
    val title = context.resources.getString(R.string.share)
    val shareIntentWithChooser = Intent.createChooser(shareIntent, title)
    if (shareIntentWithChooser.resolveActivity(context.applicationContext.packageManager) != null) {
        context.startActivity(shareIntentWithChooser)
    }
}

fun openTheUrl(context: Context, article: Article) {
    val browserIntent = Intent(Intent.ACTION_VIEW)
    browserIntent.data = Uri.parse(article.url)
    context.startActivity(browserIntent)
}

fun getSmallImage(article: Article, imageView: ImageView) {
    Picasso
        .get()
        .load(article.urlToImage)
        .centerCrop()
        .resize(500, 500)
        .placeholder(R.drawable.ic_broken_image)
        .error(R.drawable.ic_connection_error)
        .into(imageView)
}

fun getBigImage(
    context: Context,
    article: Article,
    imageView: ImageView
) {
    val metrics = context.resources.displayMetrics
    val height = metrics.heightPixels
    val width = (height / 1.6).toInt()
    Picasso
        .get()
        .load(article.urlToImage)
        .centerInside()
        .resize(width, height)
        .placeholder(R.drawable.ic_broken_image)
        .error(R.drawable.ic_connection_error)
        .into(imageView)
}
