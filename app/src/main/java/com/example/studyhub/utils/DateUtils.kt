package com.example.studyhub.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.capitalize
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun getToday() : String {
    val today = LocalDate.now()
    val dayOfWeek = today.dayOfWeek
    val dayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale("ru"))
    return dayName.capitalizeFirstLetter()
}

fun String.capitalizeFirstLetter(): String {
    return this.replaceFirstChar { it.uppercase() }
}
