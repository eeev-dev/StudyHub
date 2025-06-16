package com.example.studyhub.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.capitalize
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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

@RequiresApi(Build.VERSION_CODES.O)
fun getNearestWeekdayDate(shortDay: String): String {
    val dayOfWeekMap = mapOf(
        "Пн" to DayOfWeek.MONDAY,
        "Вт" to DayOfWeek.TUESDAY,
        "Ср" to DayOfWeek.WEDNESDAY,
        "Чт" to DayOfWeek.THURSDAY,
        "Пт" to DayOfWeek.FRIDAY,
        "Сб" to DayOfWeek.SATURDAY,
        "Вс" to DayOfWeek.SUNDAY
    )

    val targetDay = dayOfWeekMap[shortDay]
        ?: throw IllegalArgumentException("Неверный день недели: $shortDay")

    val today = LocalDate.now()
    var daysUntilTarget = (targetDay.value - today.dayOfWeek.value + 7) % 7

    if (daysUntilTarget == 0) daysUntilTarget = 7

    val nearestDate = today.plusDays(daysUntilTarget.toLong())

    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return nearestDate.format(formatter)
}
