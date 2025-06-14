package com.example.studyhub.utils

import com.example.studyhub.data.local.entities.ScheduleEntity
import com.example.studyhub.data.remote.models.Schedule
import kotlin.Int

fun Schedule.toEntity(): ScheduleEntity {
    return ScheduleEntity(
        id = id?.toInt() ?: 0,
        title = title,
        type = type,
        teacher = teacher,
        group = group,
        term = term,
        day = day,
        time = time,
        parity = parity,
        is_online = is_online,
        link = link,
        room = room
    )
}

fun ScheduleEntity.toSchedule(): Schedule {
    return Schedule(
        id = id,
        title = title,
        type = type,
        teacher = teacher,
        group = group,
        term = term,
        day = day,
        time = time,
        parity = parity,
        is_online = is_online,
        link = link,
        room = room
    )
}