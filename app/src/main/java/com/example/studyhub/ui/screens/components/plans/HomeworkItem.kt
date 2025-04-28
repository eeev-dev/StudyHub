package com.example.studyhub.ui.screens.components.plans

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhub.R
import com.example.studyhub.ui.theme.sansFont

@Composable
fun DayItem(day: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(IntrinsicSize.Min).padding(bottom = 15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .wrapContentWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(colorResource(R.color.dark_blue))
        ) {
            Text(
                text = day,
                fontFamily = sansFont,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.Center).padding(horizontal = 4.dp),
                color = Color.White
            )
        }
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            HomeworkItem(
                "С̶Р̶С̶",
                "✓ Завершено за 11 дек.",
                "Информатика"
            )
            HomeworkItem(
                "ЛАБ4: Массивы",
                "\uD83D\uDD52 11 дек. 2025 г.",
                "Программирование"
            )
        }
    }
}

@Composable
fun HomeworkItem(
    title: String,
    status: String,
    subject: String
) {
    Column(
        modifier = Modifier.padding(vertical = 10.dp)
    ) {
        CustomText(title)
        CustomText(status)
        CustomText(subject)
    }
}

@Composable
fun CustomText(text: String) {
    Text(
        text = text,
        fontFamily = sansFont,
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 5.dp),
        color = Color.Black
    )
}