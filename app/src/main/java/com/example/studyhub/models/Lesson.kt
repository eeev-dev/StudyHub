package com.example.studyhub.models

data class Lesson(
    val title: String,
    val isOnline: Pair<Boolean, String>,
    val time: String,
    val type: String,
    var teacher: String = "Преподаватель"
)

val schedule: Map<String, List<Lesson>> = mapOf(
    "Понедельник" to listOf(
        Lesson("Математика", false to "2/628", "10:00-11:20", "лк"),
        Lesson("Физика", true to "https://www.youtube.com/watch?v=Rk5oSZ1NrcQ", "11:30-12:50", "пр")
    ),
    "Вторник" to listOf(
        Lesson("Информатика", false to "1/314", "10:00-11:20", "лб")
    ),
    "Среда" to listOf(
        Lesson("История", true to "https://www.youtube.com/watch?v=jeK6Z6eR1Ls", "11:30-12:50", "лк"),
        Lesson("Английский", false to "2/345", "13:00-14:20", "пр")
    ),
    "Четверг" to listOf(
        Lesson("Программирование", true to "https://www.youtube.com/watch?v=Hg8l1ZAza3I", "12:00-13:20", "лб")
    ),
    "Пятница" to listOf(
        Lesson("Физкультура", false to "Стадион", "14:30-15:50", "пр"),
        Lesson("Английский", false to "2/524", "13:00-14:20", "пр")
    )
)
