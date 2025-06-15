package com.example.studyhub.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@RequiresApi(Build.VERSION_CODES.O)
fun isDatePassed(date: LocalDate): Boolean {
    return date.isBefore(LocalDate.now())
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseDeadlineToLocalDate(deadlineStr: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    val zonedDateTime = ZonedDateTime.parse(deadlineStr, formatter)
    return zonedDateTime.toLocalDate()
}

fun formatDateRussian(inputDate: String): String {
    val inputFormat = SimpleDateFormat("dd.MM.yyyy", Locale("ru"))
    val outputFormat = SimpleDateFormat("dd MMM yyyy 'г.'", Locale("ru"))

    val date = inputFormat.parse(inputDate) ?: return inputDate
    return outputFormat.format(date)
}

fun formatDate(input: String): String {
    val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

    return try {
        val date = inputFormat.parse(input)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        println("Ошибка даты: ${e}")
        "Неверная дата"
    }
}

fun isDateInPast(date: String): Boolean {
    val dateString = convertGmtToLocal(date)
    return try {
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
        formatter.timeZone = TimeZone.getDefault() // учитываем локальное время

        val parsedDate = formatter.parse(dateString)
        val now = Date()

        parsedDate.before(now)
    } catch (e: Exception) {
        false
    }
}

fun convertGmtToLocal(gmtDateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH).apply {
            timeZone = TimeZone.getTimeZone("GMT")
        }

        val date = inputFormat.parse(gmtDateString) ?: return "Неверная дата"

        val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).apply {
            timeZone = TimeZone.getDefault() // локальное время устройства
        }

        outputFormat.format(date)
    } catch (e: Exception) {
        println("Ошибка парсинга: ${e}")
        "Неверная дата"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getWeekday(dateString: String): String {
    return try {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val date = LocalDate.parse(dateString, formatter)
        val dayOfWeek = date.dayOfWeek
        dayOfWeek.getDisplayName(TextStyle.SHORT, Locale("ru"))
    } catch (e: Exception) {
        ""
    }
}