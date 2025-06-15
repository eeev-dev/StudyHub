package com.example.studyhub.ui.screens.plans.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.studyhub.R
import com.example.studyhub.ui.theme.sansFont

@Composable
fun ExpiredItem(
    title: String,
    onUpdate: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            fontFamily = sansFont,
            fontSize = 20.sp,
            modifier = Modifier
                .weight(3f)
                .padding(start = 5.dp)
                .align(Alignment.CenterVertically),
            color = Color.White
        )
        Spacer(Modifier.width(5.dp))
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.long_arrow),
            contentDescription = stringResource(R.string.icon),
            Modifier
                .weight(0.5f)
                .size(36.dp)
                .graphicsLayer { rotationZ = 180f }
                .clickable { onUpdate() },
            colorFilter = ColorFilter.tint(Color.White)
        )
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.delete),
            contentDescription = stringResource(R.string.icon),
            Modifier
                .weight(0.5f)
                .size(36.dp)
                .align(Alignment.CenterVertically)
                .clickable { onDelete() },
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}