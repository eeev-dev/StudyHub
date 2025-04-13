package com.example.studyhub.ui.screens.schedule.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studyhub.R
import com.example.studyhub.ui.theme.sansFont

@Composable
fun LessonItem(
    time: String,
    title: String,
    type: String
) {
    Box(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min).padding(start = 8.dp, end = 8.dp, top = 12.dp)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.background(Color.White)
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.desktop),
                contentDescription = stringResource(R.string.icon),
                Modifier.weight(1f).aspectRatio(1f).align(Alignment.CenterVertically)
            )
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                modifier = Modifier.weight(5f).padding(8.dp)
            ) {
                Box(modifier = Modifier.background(colorResource(R.color.dark_blue)).padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = time,
                            fontFamily = sansFont,
                            fontSize = 20.sp,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
                            color = Color.White
                        )
                        Text(
                            text = title,
                            fontFamily = sansFont,
                            fontSize = 20.sp,
                            modifier = Modifier.weight(2f).align(Alignment.CenterVertically),
                            color = Color.White
                        )
                        Text(
                            text = type,
                            fontFamily = sansFont,
                            fontSize = 20.sp,
                            modifier = Modifier.weight(1f).align(Alignment.CenterVertically),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}