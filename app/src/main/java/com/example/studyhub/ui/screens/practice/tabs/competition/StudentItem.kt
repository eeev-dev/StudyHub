package com.example.studyhub.ui.screens.practice.tabs.competition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhub.R
import com.example.studyhub.ui.screens.practice.tabs.Student
import com.example.studyhub.ui.theme.sansFont

@Composable
fun StudentItem(
    place: Int,
    name: String,
    scores: Double
) {
    BoxWithConstraints(modifier = Modifier.padding(bottom = 6.dp)) {
        val scaleFactor = if (maxWidth < 300.dp) 0.8f else 1f

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp * scaleFactor)
                    .background(colorResource(R.color.blue), CircleShape)
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 4.dp)
            ) {
                Text(
                    fontFamily = sansFont,
                    text = "$place",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                    fontSize = 24.sp * scaleFactor
                )
            }

            Text(
                fontFamily = sansFont,
                text = name,
                fontSize = 20.sp * scaleFactor,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 8.dp)
                    .weight(4f)
            )

            Text(
                fontFamily = sansFont,
                text = "$scores",
                fontSize = 20.sp * scaleFactor,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(horizontal = 4.dp)
                    .weight(1f)
            )
        }
    }
}
