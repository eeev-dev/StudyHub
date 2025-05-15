package com.example.studyhub.ui.screens.practice.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.studyhub.ui.screens.practice.tabs.competition.ProgressBar
import com.example.studyhub.ui.screens.practice.tabs.competition.StudentItem

data class Student(
    val name: String,
    val scores: Double
)

@Composable
fun CompetitionTab() {
    val students = listOf(
        Pair("Абдылдаева К. Л.", 93.7),
        Pair("Мусаев О. Ч.", 90.1),
        Pair("Кулатова А. И.", 82.6),
        Pair("Усенов Е. Ж.", 80.0),
        Pair("Чынгышев У. М.", 74.3)
    ).sortedByDescending { it.second }
    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
        ProgressBar(6, 4)
        LazyColumn(
            modifier = Modifier.fillMaxHeight()
        ) {
            students.forEachIndexed { index, student ->
                item { StudentItem(index + 1, student.first, student.second) }
            }
            item {
                Spacer(modifier = Modifier.height(90.dp).background(Color.Transparent))
            }
        }
    }
}