package com.example.studyhub.ui.model

import com.example.studyhub.data.remote.models.Schedule

data class Lesson(
    val title: String,
    val isOnline: Pair<Boolean, String>,
    val time: String,
    val type: String,
    var teacher: String = "Преподаватель"
)

fun convertSchedule(scheduleEntities: List<Schedule>): Map<String, List<Lesson>> {
    return scheduleEntities
        .filter { it.day != null }
        .groupBy { it.day!! }
        .mapValues { (_, entities) ->
            entities.map { entity ->
                Lesson(
                    title = entity.title ?: "Без названия",
                    isOnline = if (entity.is_online == 1)
                        true to (entity.link ?: "Нет ссылки")
                    else
                        false to (entity.room ?: "Нет аудитории"),
                    time = entity.time ?: "00:00",
                    type = entity.type ?: "неизвестно",
                    teacher = entity.teacher ?: "Преподаватель"
                )
            }
        }
}
