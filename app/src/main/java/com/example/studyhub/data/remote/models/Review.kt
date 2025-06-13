package com.example.studyhub.data.remote.models

data class Review (
    val date: String,
    val id: Int,
    val id_place: Int,
    val rating: Int,
    val text: String
)