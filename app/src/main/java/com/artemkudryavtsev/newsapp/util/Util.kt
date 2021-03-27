package com.artemkudryavtsev.newsapp.util

import com.artemkudryavtsev.newsapp.settings.languageandregion.Countries
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
        val df_input = SimpleDateFormat(inputFormat, Locale.getDefault())
        val df_output = SimpleDateFormat(outputFormat, Locale.getDefault())
        try {
            parsed = df_input.parse(dateOfPublishing)
            outputDate = df_output.format(parsed)
        } catch (e: ParseException) {
            Timber.i("$e")
        }
        return outputDate
    } else {
        return dateOfPublishing
    }
}
