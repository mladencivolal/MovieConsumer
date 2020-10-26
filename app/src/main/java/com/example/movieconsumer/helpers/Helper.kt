package com.example.movieconsumer.helpers

import android.view.View
import java.text.DateFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun formatCurrency(amount: Int): String {
    val format: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    format.maximumFractionDigits = 0
    return format.format(amount)
}

fun dateFormatter(input: String): String {
    var dateFormat: DateFormat = SimpleDateFormat("yyyy-mm-dd", Locale.US)
    val date: Date = dateFormat.parse(input)
    dateFormat = SimpleDateFormat("d MMMM yyyy", Locale.US)
    return dateFormat.format(date)
}

fun shortenString(input: String, length: Int): String {
    var text = input
    if (input.length >= length) {
        text = input.substring(0..length-1)
        if (text.endsWith(" ") || text.endsWith(",")) {
            text.removeRange(text.length - 2, text.length - 1)
        }
        text+="..."
    }
    return text
}

fun View.visible(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}