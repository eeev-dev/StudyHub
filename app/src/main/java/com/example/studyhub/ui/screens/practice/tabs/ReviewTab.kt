package com.example.studyhub.ui.screens.practice.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.studyhub.ui.components.ClipboardTextField
import com.example.studyhub.ui.screens.practice.components.SendConfirmation
import com.example.studyhub.ui.screens.practice.tabs.competition.StudentItem
import com.example.studyhub.ui.screens.practice.tabs.review.RatingBar
import com.example.studyhub.ui.screens.practice.tabs.review.ReviewItem
import com.example.studyhub.ui.screens.practice.tabs.review.Statistics

@Composable
fun ReviewTab() {
    var dialogState by remember { mutableStateOf(false) }
    var rating by remember { mutableIntStateOf(0) }
    if (dialogState) {
        SendConfirmation({ dialogState = false }, "Оставить отзыв") {
            Column {
                RatingBar(
                    rating,
                    onRatingChanged = { newRating ->
                        rating = newRating
                    }
                )
                var text by remember { mutableStateOf("") }

                ClipboardTextField(
                    value = text,
                    label = "Отзыв",
                    onValueChange = { text = it }
                )

            }
        }
    }
    Column {
        Statistics { dialogState = true }
        val reviews = listOf(
            Pair(
                5,
                "Работал над реальными задачами, познакомился с классной командой, получил много нового опыта и знаний"
            ),
            Pair(3, "задачи были скучные и однотипные, практически не было обратной связи")
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight()
        ) {
            reviews.forEach { review ->
                item {
                    ReviewItem(
                        review.first,
                        review.second,
                        "12.07.24"
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(90.dp).background(Color.Transparent))
            }
        }
    }
}