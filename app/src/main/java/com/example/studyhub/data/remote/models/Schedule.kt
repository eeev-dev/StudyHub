package com.example.studyhub.data.remote.models

data class Schedule(
    val id: Int?,
    val title: String?,
    val type: String?,
    val teacher: String?,
    val group: String?,
    val term: String?,
    val day: String?,
    val time: String?,
    val parity: String?,
    val is_online: Int?,
    val link: String?,
    val room: String?
)