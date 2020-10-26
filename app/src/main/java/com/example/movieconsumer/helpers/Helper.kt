package com.example.movieconsumer.helpers

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