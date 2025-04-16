package com.example.studyhub.ui.screens.schedule

data class Lesson(
    val title: String,
    val isOnline: Pair<Boolean, String>,
    val time: String,
    val type: String // "лк" (лекция), "пр" (практика), "лб" (лабораторная)
)

val schedule: Map<String, List<Lesson>> = mapOf(
    "Понедельник" to listOf(
        Lesson("Математика", false to "2/628", "09:00", "лк"),
        Lesson("Физика", true to "https://www.youtube.com/watch?v=Rk5oSZ1NrcQ", "11:00", "пр")
    ),
    "Вторник" to listOf(
        Lesson("Информатика", false to "1/314", "10:00", "лб")
    ),
    "Среда" to listOf(
        Lesson("История", true to "https://www.youtube.com/watch?v=jeK6Z6eR1Ls", "08:30", "лк"),
        Lesson("Английский", false to "", "13:00", "пр")
    ),
    "Четверг" to listOf(
        Lesson("Программирование", true to "https://www.youtube.com/watch?v=Hg8l1ZAza3I", "12:00", "лб")
    ),
    "Пятница" to listOf(
        Lesson("Физкультура", false to "Стадион", "15:00", "пр"),
        Lesson("Английский", false to "2/524", "13:00", "пр")
    )
)
