package com.example.studyhub.ui.screens.scores.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhub.R
import com.example.studyhub.ui.theme.sansFont

@Composable
fun ScoreItem(title: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.blue)),
        modifier = Modifier.padding(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = title,
                fontFamily = sansFont,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(4f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp),
                color = Color.White
            )
            Text(
                text = "23",
                fontFamily = sansFont,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp),
                color = Color.White
            )
            Text(
                text = "28",
                fontFamily = sansFont,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp),
                color = Color.White
            )
            Text(
                text = "51",
                fontFamily = sansFont,
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(start = 5.dp),
                color = Color.White
            )
        }
    }
}