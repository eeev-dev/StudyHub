package com.example.studyhub.data.local.model

data class Subject(
    val type: String,
    val teacher: String
)

val subjects: Map<String, List<Subject>> = mapOf(
    "Операционные системы" to listOf(
        Subject("ЛБ", "Бакасова П. С."),
        Subject("ЛК", "Исраилова Н. А.")
    ),
    "Математическая логика" to listOf(
        Subject("", "Тентиева С. М.")
    ),
    "Программирование" to listOf(
        Subject("ЛК", "Тультемирова Г.У."),
        Subject("ЛБ", "Эркинбек к. А."),
        Subject("ПР", "Эркинбек к. А.")
    )
)