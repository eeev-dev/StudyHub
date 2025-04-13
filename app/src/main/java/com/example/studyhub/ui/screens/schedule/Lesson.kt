package com.example.studyhub.ui.screens.schedule

data class Lesson(
    val title: String,
    val isOnline: Boolean,
    val time: String,
    val type: String // "лк" (лекция), "пр" (практика), "лб" (лабораторная)
)

val schedule: Map<String, List<Lesson>> = mapOf(
    "Понедельник" to listOf(
        Lesson("Математика", false, "09:00", "лк"),
        Lesson("Физика", true, "11:00", "пр")
    ),
    "Вторник" to listOf(
        Lesson("Информатика", false, "10:00", "лб")
    ),
    "Среда" to listOf(
        Lesson("История", true, "08:30", "лк"),
        Lesson("Английский", false, "13:00", "пр")
    ),
    "Четверг" to listOf(
        Lesson("Программирование", true, "12:00", "лб")
    ),
    "Пятница" to listOf(
        Lesson("Физкультура", false, "15:00", "пр"),
        Lesson("Английский", false, "13:00", "пр")
    )
)
