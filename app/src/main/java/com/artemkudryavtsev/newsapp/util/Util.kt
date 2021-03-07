package com.artemkudryavtsev.newsapp.util

import com.artemkudryavtsev.newsapp.settings.languageandregion.Countries

fun getCountryByCode(countryCode: String): String {
    for (country in Countries.countries) {
        if (countryCode == country.first) {
            return country.second
        }
    }
    return "United States of America"
}
