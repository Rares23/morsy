package com.crxapplications.morsy.core.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.pretty(format: String = "MMM dd, yyyy", lang: String = "en"): String {
    val formatter = SimpleDateFormat(format, Locale(lang))
    return formatter.format(this)
}