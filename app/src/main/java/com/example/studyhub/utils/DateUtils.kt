package com.example.studyhub.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.capitalize
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun getToday(): String {
    val today = LocalDate.now()
    val dayOfWeek = today.dayOfWeek
    val dayNameShort = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ru"))
    return dayNameShort.capitalizeFirstLetter()
}

fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar { it.uppercase() }
}

@RequiresApi(Build.VERSION_CODES.O)
val shortWeekDays = DayOfWeek.values().map {
    it.getDisplayName(TextStyle.SHORT, Locale("ru")).capitalizeFirstLetter()
}.toList().dropLast(1)
